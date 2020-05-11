package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import test.db.JDBCUtil;
import test.vo.MovieVo;

public class MovieDao {
	private static MovieDao instance = new MovieDao();
	private MovieDao() {};
	public static MovieDao getInstance() {
		return instance;
	}
	public MovieVo getinfo(int mnum) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from movie where mnum=?";
		try {
			con=JDBCUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mnum);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				MovieVo vo= new MovieVo(
						rs.getInt("mnum"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getString("director"));
						
						return vo;
			}
			return null;
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}
}
