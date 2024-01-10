package ru.cft.shiftlab.contentmaker.exceptionhandling;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация об ошибке.")
public class StaticContentException extends RuntimeException{

    @Schema(description = "Сообщение с информацией об ошибке.",
            example = "История не добавлена.")
    String message;

    @Schema(description = "Код ошибки.",
            example = "404")
    String code;
}
