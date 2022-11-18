package com.example.test_sqlite.Utils.Database.PreSet;

public enum QUERY {//Q:QUERY->whereClause
    Q_AccountBook_byId("Id=?"),
    //time
    Q_AccountBook_byYear("year=?"),
    Q_AccountBook_byMonth("month=?"),
    Q_AccountBook_byDay("day=?"),
    //info
    Q_AccountBook_byAmount("money=?"),
    Q_AccountBook_byType("type = ?"),
    Q_AccountBook_byDescription("Description like ?");//use different match string

    private QUERY(String queryClause) {
        statement = queryClause;
    }

    private final String statement;

    @Override
    public String toString() {
        return statement;
    }
}
