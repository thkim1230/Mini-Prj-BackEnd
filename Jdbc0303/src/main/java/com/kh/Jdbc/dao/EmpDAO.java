package com.kh.Jdbc.dao;
import com.kh.Jdbc.util.Common;
import com.kh.Jdbc.vo.EmpVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class EmpDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public List<EmpVO> empSelect() {
        List<EmpVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM EMP";
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                int empNO = rs.getInt("EMPNO");
                String name = rs.getString("ENAME");
                String job = rs.getString("JOB");
                int mgr = rs.getInt("MGR");
                Date date = rs.getDate("HIREDATE");
                int sal = rs.getInt("SAL");
                int comm = rs.getInt("COMM");
                int deptNo = rs.getInt("DEPTNO");

                EmpVO vo = new EmpVO(empNO, name, job, mgr, date, sal, comm, deptNo);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<EmpVO> empInsert(EmpVO empVo) {

        String sql = "INSERT INTO EMP(EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) VALUES (?,?,?,?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empVo.getEmpNO());
            pstmt.setString(2, empVo.getName());
            pstmt.setString(3, empVo.getJob());
            pstmt.setInt(4, empVo.getMgr());
            pstmt.setDate(5, empVo.getDate());
            pstmt.setInt(6, (int)empVo.getSal());
            pstmt.setInt(7, (int)empVo.getComm());
            pstmt.setInt(8, empVo.getDeptNO());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
        return null;
    }

    public void empDelete() {
        System.out.print("삭제할 이름을 입력 하세요 : ");
        String name = sc.next();
        String sql = "DELETE FROM EMP WHERE ENAME = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }
    public void empUpdate() {
        System.out.print("변경할 사원의 이름을 입력 하세요 : ");
        String name = sc.next();
        System.out.print("직책 : ");
        String job = sc.next();
        System.out.print("급여 : " );
        int sal = sc.nextInt();
        System.out.print("성과급 : " );
        int comm = sc.nextInt();

        String sql = "UPDATE EMP SET JOB = ?, SAL = ?, COMM = ? WHERE ENAME = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, job);
            pstmt.setInt(2, sal);
            pstmt.setInt(3,  comm);
            pstmt.setString(4, name);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public void empSelectRst(List<EmpVO> list) {
        for(EmpVO e : list) {
            System.out.print(e.getEmpNO() + " ");
            System.out.print(e.getName() + " ");
            System.out.print(e.getJob() + " ");
            System.out.print(e.getMgr() + " ");
            System.out.print(e.getDate() + " ");
            System.out.print(e.getSal() + " ");
            System.out.print(e.getComm() + " ");
            System.out.print(e.getDeptNO() + " ");
            System.out.println();
        }
    }
}

