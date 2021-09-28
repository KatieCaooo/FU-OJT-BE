package ojt.management.common.payload.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterUpdateRequest {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
}
