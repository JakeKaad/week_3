public interface Saveable {

  public static List<Object> all() {
    String sql = "SELECT * FROM " + this.DB_TABLE;
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(this.getClass);
    }
  }
}
