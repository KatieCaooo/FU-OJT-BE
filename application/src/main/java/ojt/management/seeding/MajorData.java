package ojt.management.seeding;

import ojt.management.data.entities.Major;

import java.util.Arrays;
import java.util.List;

public class MajorData {
    public static List<Major> getSeedData() {
        List<Major> majors = Arrays.asList(
                new Major("Software Engineering"),
                new Major("Digital Art Design"),
                new Major("Information Security"),
                new Major("Information System"),
                new Major("Artificial Intelligence"),
                new Major("IoT"),
                new Major("Business Administration"),
                new Major("International Business"),
                new Major("Digital Marketing"),
                new Major("Tourism and Holiday Service Administration"),
                new Major("Multifunctional Communication Administration"),
                new Major("Hotel Management"),
                new Major("Japanese"),
                new Major("Korean")
        );
        return majors;
    }
}
