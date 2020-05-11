package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import test.db.JDBCUtil;
import test.vo.CommentsVo;

public class CommentsDao {
	private static CommentsDao instance = new CommentsDao();
	private CommentsDao() {};
	public static CommentsDao getInstance() {
		return instance;
	}
	public int insert(CommentsVo vo) {
		String sql="insert into comments values(comments_seq.nextval,?,?,?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con=JDBCUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, vo.getMnum());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getComments());
			pstmt.executeUpdate();
			return pstmt.executeUpdate();
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		}finally {
			JDBCUtil.close(null,pstmt,con);
		}
	}
	
	public ArrayList<CommentsVo> listAll(int mnum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con=JDBCUtil.getConn();
			pstmt=con.prepareStatement("select * from comments where mnum=?");
			pstmt.setInt(1, mnum);
			rs=pstmt.executeQuery();
			ArrayList<CommentsVo> list= new ArrayList<CommentsVo>();
			while(rs.next()) {
				CommentsVo vo = new CommentsVo(
						rs.getInt("num"),
						rs.getInt("mnum"),
						rs.getString("id"),
						rs.getString("comments"));
						list.add(vo);
				}
			return list;
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}
	
	public int delete(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql="delete comments where num=?";
		try {
			con=JDBCUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			return pstmt.executeUpdate(); 
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		}finally {
			JDBCUtil.close(null, pstmt, con);
		}
		
	}
}
