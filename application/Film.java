package application;

import java.util.List;

public class Film {
    private String code;
    private String name;
    private String theme;
    private String[] producers;
    private String[] mainActors;
    private int productionYear;
    private List<String> comments;

    public Film(String code, String name ,String theme, String[] producers, String[] mainActors, int productionYear,  List<String> comments) {
        this.code = code;
        this.name = name;
        this.theme = theme;
        this.producers = producers;
        this.mainActors = mainActors;
        this.productionYear = productionYear;
        this.comments = comments;
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String[] getProducers() { return producers; }
    public void setProducers(String[] producers) { this.producers = producers; }

    public String[] getMainActors() { return mainActors; }
    public void setMainActors(String[] mainActors) { this.mainActors = mainActors; }

    public int getProductionYear() { return productionYear; }
    public void setProductionYear(int productionYear) { this.productionYear = productionYear; }

	public boolean isCommentsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}


