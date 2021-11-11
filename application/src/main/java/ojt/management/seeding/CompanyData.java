package ojt.management.seeding;

import com.github.javafaker.Faker;
import ojt.management.data.entities.Company;

import java.util.Arrays;
import java.util.List;

public class CompanyData {
    public static List<Company> getSeedData() {
        Faker faker = new Faker();
        List<Company> companies = Arrays.asList(
                new Company("Tyme",
                        "Tyme is building multi-country digital banks with high-tech and high-touch solutions.",
                        faker.address().fullAddress()),
                new Company("NAVER VIETNAM",
                        "NAVER Co., Ltd. is South Korea’s largest web search engine, as well as a global ICT brand that provides services including LINE messenger, currently with over 200 million users from around the world, the SNOW video app, and the digital comics platform NAVER WEBTOON. " +
                        "NAVER BAND, a group social media platform, boasts one million monthly active users. " +
                        "The sustained research and development of AI, robotics, mobility, and other future technology trends are propelling NAVER forward, in pursuit of the transformation and innovation of technology platforms, while it also remains devoted to a paradigm of shared growth, joining hands with users from the global community as well as a vast number of partnerships. " +
                        "In 2018, NAVER was ranked as the 9th most innovative company by Forbes and top 6th Future 50 company by Fortune magazine.",
                        faker.address().fullAddress()),
                new Company("Magnolia",
                        "Magnolia started small, driven by two innovators who wanted to build a flexible and powerful CMS. " +
                        "Over the years, we've extended this vision to make life easier for our customers and partners - to understand your DX challenges and to ensure that each deployment of the platform is truly tailored to what you want. " +
                        "It is a privately-held company with headquarters in Basel, Switzerland. " +
                        "Today, Magnolia has a global reach, with 9 regional offices serving customers in over 100 countries. " +
                        "Magnolia Vietnam is 30+ members strong, rapidly expanding to meet growing business needs.",
                        faker.address().fullAddress()),
                new Company("Skedulo Vietnam",
                        "Skedulo is the platform for intelligent mobile workforce management. " +
                        "Our solution helps enterprises intelligently schedule, dispatch and track resources in the field." +
                        " Skedulo connects your teams at HQ–like operations, scheduling and the back office–with your mobile workers in the field.",
                        faker.address().fullAddress()),
                new Company("MOSO",
                        "MOSO builds products that help small companies transform their businesses. " +
                        "Designed to be flexible and scalable, our solutions enable companies to deliver excellent services, increase their market shares and lower operating costs. " +
                        "LenderRate is founded by a group of engineers, business owners and investors, based in Silicon Valley, USA.",
                        faker.address().fullAddress()),
                new Company("Carp Tech Corporation", "Founded 2014, we are a group of Microsoft certified developers who want to solve business challenge with SharePoint. " +
                        "We also using .Net technology to provide warehouse and service management systems to customer which integrated seamlessly in to current system or ERP. " +
                        "Although the usage of cloud is growing but our customer still need on-premise solutions so we expand to hardware domain our customers can get " +
                        "more benefit from our whole IT solution from us.",
                        faker.address().fullAddress()),
                new Company("CodeHQ (formerly Augen)",
                        "CodeHQ (formerly Augen Software Group) is a New Zealand-owned software services provider. " +
                        "Our parent office in New Zealand was established in Auckland in 1993 and our subsidiary office, CodeHQ Vietnam, was established in Ho Chi Minh City in 2005. " +
                        "Our customers are in many industry sectors, including Banking, Finance, Insurance, Healthcare, " +
                        "IT Infrastructure, Manufacturing, Professional Services, Software Innovation, Transport and Logistics, to name a few.", faker.address().fullAddress()),
                new Company("Nakivo",
                        "NAKIVO has an impressive track record of 5-star community reviews and over 97% customer satisfaction with support, and has also won a Best of VMworld 2018” Gold Award for Data Protection at VMworld 2018 US. " +
                                "Customers trust us and we aim to deliver on that trust.",
                        faker.address().fullAddress())
        );
        return companies;
    }
}
