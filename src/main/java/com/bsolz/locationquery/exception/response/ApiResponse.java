package com.bsolz.locationquery.exception.response;

import java.sql.Timestamp;
import java.util.Date;

public record ApiResponse(int statusCode, String message, String currentDate) {
}
