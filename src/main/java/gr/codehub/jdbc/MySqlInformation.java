package gr.codehub.jdbc;

public class MySqlInformation implements DatabaseInformation {

    @Override
    public String getUserName() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "root";
    }

    @Override
    public String getUrl() {
        return "jdbc:mysql://localhost:3306";
    }

    @Override
    public String getDatabase() {
        return "sakila";
    }
    
}
