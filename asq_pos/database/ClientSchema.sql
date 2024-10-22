SET SERVEROUTPUT ON SIZE 10000
SET FLUSH OFF;
SET TRIMSPOOL OFF;

SPOOL dbdefine.log;
-- ***************************************************************************
-- Place any customer specific schema changes into this file
--
-- All sql statements that goes here should be defined in a update-safe way, ie. the final script should be able 
-- to run against the database any number of times and should produce the same output.
--
-- This file should finish with a database commit; at least at the end of the file.
--
-- ***************************************************************************

-- ***************************************************************************

--
-- Variables
--
DEFINE dbDataTableSpace = '$(DbTblspace)_DATA';-- Name of data file tablespace
DEFINE dbIndexTableSpace = '$(DbTblspace)_INDEX';-- Name of index file tablespace 

-- Oracle db specific command. Remove or comment this line out for all other DBMS.
ALTER SESSION SET CURRENT_SCHEMA=$(DbSchema);
-- ***************************************************************************
DECLARE
    li_rowcnt       int;
BEGIN
    SELECT count(*) INTO li_rowcnt
    FROM ALL_TABLES where table_name = 'ASQ_ZATCA_INVOICE_STAGING';
    IF li_rowcnt = 0 THEN
dbms_output.put_line('--- CREATING TABLE ASQ_ZATCA_INVOICE_STAGING --- ');
execute immediate 'CREATE TABLE ASQ_ZATCA_INVOICE_STAGING 
   (ORGANIZATION_ID NUMBER(10, 0) NOT NULL, 
	ICV NUMBER(17,6) NOT NULL, 
	BUSINESS_DATE TIMESTAMP (6) NOT NULL, 
	TRANS_SEQ VARCHAR2(10 BYTE) NOT NULL, 
	WKSTN_ID VARCHAR2(5 BYTE) NOT NULL, 
	INVOICE_ID VARCHAR2(200 BYTE) NOT NULL, 
	INVOICE_UUID VARCHAR2(200 BYTE), 
	INVOICE_HASHCODE VARCHAR2(250 BYTE), 
	INVOICE_DATE TIMESTAMP (6), 
	STATUS VARCHAR2(20 BYTE), 
	INVOICE_QRCODE BLOB, 
	INVOICE_XML BLOB, 
	CREATE_DATE TIMESTAMP (6), 
	CREATE_USER_ID VARCHAR2(20 BYTE), 
	UPDATE_USER_ID VARCHAR2(20 BYTE), 
	UPDATE_DATE TIMESTAMP (6), 
	 CONSTRAINT PK_ASQ_ZATCA_INVOICE_STAGING PRIMARY KEY (ICV, ORGANIZATION_ID, INVOICE_ID, BUSINESS_DATE, TRANS_SEQ, WKSTN_ID)
  USING INDEX TABLESPACE &dbIndexTableSpace.
  ) 
  TABLESPACE &dbDataTableSpace.'
  ;
  EXECUTE IMMEDIATE 'GRANT SELECT,INSERT,UPDATE,DELETE ON ASQ_ZATCA_INVOICE_STAGING TO POSUSERS,DBAUSERS';

  CREATE_PROPERTY_TABLE('ASQ_ZATCA_INVOICE_STAGING');
    END IF;
END;
/
