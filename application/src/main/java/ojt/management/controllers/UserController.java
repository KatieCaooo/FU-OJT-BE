package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.AccountService;
import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.UserDTO;
import ojt.management.common.payload.request.AccountUpdateRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.data.entities.Account;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.UserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final AccountService accountService;
    private final UserMapper userMapper;

    public UserController(AccountService accountService, UserMapper userMapper) {
        this.accountService = accountService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws AccountIdNotExistedException {
        return userMapper.userToUserDTO(accountService.getUserById(id));
    }

    @GetMapping()
    public PagedDataResponse<UserDTO> searchUser(@RequestParam(value = "search", required = false) String search,
                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Account> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(
                Optional.ofNullable(pageNo).orElse(0),
                Optional.ofNullable(pageSize).orElse(20),
                sort);
        Page<Account> pagedResult = accountService.searchUser(spec, pageable);
        List<UserDTO> data = pagedResult.getContent().stream().map(userMapper::userToUserDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@Valid @RequestBody AccountUpdateRequest accountUpdateRequest) throws AccountIdNotExistedException {
        return userMapper.userToUserDTO(accountService.updateUser(accountUpdateRequest.getId(),
                accountUpdateRequest.getPhone(), accountUpdateRequest.getAddress(), accountUpdateRequest.getPassword()));

    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) throws AccountIdNotExistedException {
        return accountService.deleteUser(id);
    }
}
