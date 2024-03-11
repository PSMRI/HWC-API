/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.annotation.sqlInjectionSafe;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLInjectionSafeConstraintValidator implements ConstraintValidator<SQLInjectionSafe, String> {


    public static final String SQL_TYPES =
            "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY" +
            "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, " +
            "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR" +
            "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, " +
            "COLUMN, FIELD, OPERATOR";

    private static final String[] sqlRegexps = {
            "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)",
            "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
            "(.*)(/\\*|\\*/|;){1,}(.*)",
            "(.*)(-){2,}(.*)",

    };

    // pre-build the Pattern objects for faster validation
    private List<Pattern> validationPatterns = getValidationPatterns();

    @Override
    public void initialize(SQLInjectionSafe sqlInjectionSafe) { }

    @Override
    public boolean isValid(String dataString, ConstraintValidatorContext cxt) {
        return isSqlInjectionSafe(dataString);
    }

    private boolean isSqlInjectionSafe(String dataString){
        if(isEmpty(dataString)){
            return true;
        }

        for(Pattern pattern : validationPatterns){
            if(matches(pattern, dataString)){
                return false;
            }
        }
        return true;
    }

    private boolean matches(Pattern pattern, String dataString){
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> getValidationPatterns(){
        List<Pattern> patterns = new ArrayList<Pattern>();
        for(String sqlExpression : sqlRegexps){
            patterns.add(getPattern(sqlExpression));
        }
        return patterns;
    }


    private static Pattern getPattern(String regEx){
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

}

