package xyz.connect.post.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public class FileListValidator implements
        ConstraintValidator<CheckContentType, List<MultipartFile>> {

    private Set<String> validMediaType;

    @Override
    public void initialize(CheckContentType constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        validMediaType = new HashSet<>(List.of(
                "image/gif", "image/jfif", "image/pjpeg", "image/jpeg", "image/pjp",
                "image/jpg", "image/png", "image/bmp", "image/webp", "image/svgz", "image/svg",
                "image/webp")
        );
    }

    @Override
    public boolean isValid(List<MultipartFile> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        return value.stream().allMatch(file -> validMediaType.contains(file.getContentType()));
    }
}
