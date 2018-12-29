package com.example.lenovo.gymclub;

import android.provider.BaseColumns;

public final class TrainerTable {
    private TrainerTable(){}

    public class TrainerEntry implements BaseColumns{
        public static final String TABLE_NAME="trainer";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_PIC="picture";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_PHONE="phone";
    }
}
