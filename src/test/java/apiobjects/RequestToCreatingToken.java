package apiobjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RequestToCreatingToken {
    private String username;
    private String password;
}
