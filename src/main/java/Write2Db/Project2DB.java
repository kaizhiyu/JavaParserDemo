package Write2Db;

import Model.ProjectInfoTable;
import Utils.DbUtil;
import Utils.MD5Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Project2DB {
    public static final String URL = "jdbc:mysql://localhost:3306/tcdb";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        Connection conn = DbUtil.getConnection();
        String sql = "INSERT INTO project_info_table (project_id,project_name,project_type,repository_id,repository_url,repository_name,storage_time) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行
        ProjectInfoTable projectInfoTable = new ProjectInfoTable();
        projectInfoTable.setProjectName("sandbox-core");
        projectInfoTable.setProjectId(MD5Util.getMD5("99183902sandbox-core"));
//        projectInfoTable.setProjectId("000000picasso");
        projectInfoTable.setProjectType(0);
        projectInfoTable.setRepositoryId("99183902");
        projectInfoTable.setRepositoryUrl("https://github.com/alibaba/jvm-sandbox");
        projectInfoTable.setRepositoryName("jvm-sandbox");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        projectInfoTable.setStorageTime(time);
        ptmt.setString(1,projectInfoTable.getProjectId());
        ptmt.setString(2,projectInfoTable.getProjectName());
        ptmt.setLong(3,projectInfoTable.getProjectType());
        ptmt.setString(4,projectInfoTable.getRepositoryId());
        ptmt.setString(5,projectInfoTable.getRepositoryUrl());
        ptmt.setString(6,projectInfoTable.getRepositoryName());
        ptmt.setTimestamp(7,time);
        int res = ptmt.executeUpdate();//执行sql语句
        if (res > 0) {
            System.out.println("数据录入成功");
        }
        ptmt.close();//关闭资源
        conn.close();//关闭资源
    }
}
