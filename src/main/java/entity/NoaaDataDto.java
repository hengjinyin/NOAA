package entity;

public class NoaaDataDto {
    public NoaaDataDto(String stationId, String date, String weather){
        this.stationId = stationId;
        this.date = date;
        this.weather = weather;
    }
    private String stationId;
    private String date;
    private String weather;
}
