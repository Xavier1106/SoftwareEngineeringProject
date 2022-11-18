package com.example.test_sqlite.Utils.Database.PreSet;

//TODO:生成语句组合器
/*preparedStatement(enum):global constants
 * contains:
 *   F(format):column names in database tables;
 *   Q(query) :where clauses prepared for database operations
 *   C(create):create statement for database tables
 * */
public enum CREATE {
    //C:CREATE->table
    C_AccountBook(
            "CREATE TABLE ? (" +
                    "id INT PRIMARY KEY AUTOINCREMENT," +
                    "isIncome BOOLEAN," +
                    "year INT," +
                    "month INT," +
                    "day INT," +
                    "money DOUBLE," +
                    "description VARCHAR(255)," +
                    "type VARCHAR(32));"
    ),
    C_TEST("CREATE TABLE ? (" +
            "id INT," +
            "name VARCHAR(32));"
    );

    private CREATE(String createClause) {
        statement = createClause;
    }

    private final String statement;

    @Override
    public String toString() {
        return statement;
    }
}
