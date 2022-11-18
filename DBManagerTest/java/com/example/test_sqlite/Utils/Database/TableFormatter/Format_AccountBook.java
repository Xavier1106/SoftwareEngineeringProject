package com.example.test_sqlite.Utils.Database.TableFormatter;

import com.example.test_sqlite.R;
import com.example.test_sqlite.Utils.Common.PackAndParser;
import com.example.test_sqlite.Utils.Database.PreSet.CREATE;
import com.example.test_sqlite.Utils.Database.PreSet.DROP;
import com.example.test_sqlite.Utils.Database.PreSet.NAME;
import com.example.test_sqlite.Utils.Database.PreSet.QUERY;
import com.example.test_sqlite.Utils.Database.PreSet.TYPE;
import com.example.test_sqlite.Utils.Property.PPManager;

public final class Format_AccountBook extends TableFormatter {

    public Format_AccountBook(PPManager ppm) {
        create = CREATE.C_AccountBook;
        names = NAME.N_AccountBook;
        types = TYPE.T_AccountBook;
        drop = DROP.D_FORALL;
        queries = new QUERY[]{
                QUERY.Q_AccountBook_byAmount,
                QUERY.Q_AccountBook_byId,
                QUERY.Q_AccountBook_byYear,
                QUERY.Q_AccountBook_byMonth,
                QUERY.Q_AccountBook_byDay,
                QUERY.Q_AccountBook_byType,
                QUERY.Q_AccountBook_byDescription
        };
        ppm.useProperties(R.raw.db);
        tables = PackAndParser.parseStringToList((String) ppm.readFromProperties("TableNames"));
    }
}
