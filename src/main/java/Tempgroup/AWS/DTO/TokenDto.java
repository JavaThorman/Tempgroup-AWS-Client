package Tempgroup.AWS.DTO;

public class TokenDto {

    private String access_token;
    private String refresh_token;

    public TokenDto() {
    }

    public TokenDto(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "accessToken='" + access_token + '\'' +
                ", refreshToken='" + refresh_token + '\'' +
                '}';
    }

}
