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

EXEC CREATE_PROPERTY_TABLE('ASQ_ZATCA_INVOICE_STAGING');
EXEC dbms_output.put_line('--- CREATING TABLE ASQ_ZATCA_INVOICE_STAGING --- ');
CREATE TABLE "ASQ_ZATCA_INVOICE_STAGING" 
   ("ORGANIZATION_ID" VARCHAR2(2 BYTE) NOT NULL ENABLE, 
	"ICV" NUMBER NOT NULL ENABLE, 
	"BUSINESS_DATE" TIMESTAMP (6) NOT NULL ENABLE, 
	"TRANS_SEQ" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
	"WKSTN_ID" VARCHAR2(5 BYTE) NOT NULL ENABLE, 
	"INVOICE_ID" VARCHAR2(200 BYTE) NOT NULL ENABLE, 
	"INVOICE_UUID" VARCHAR2(200 BYTE), 
	"INVOICE_HASHCODE" VARCHAR2(250 BYTE), 
	"INVOICE_DATE" TIMESTAMP (6), 
	"STATUS" VARCHAR2(20 BYTE), 
	"INVOICE_QRCODE" BLOB, 
	"INVOICE_XML" BLOB, 
	"CREATE_DATE" TIMESTAMP (6), 
	"CREATE_USER_ID" VARCHAR2(20 BYTE), 
	"UPDATE_USER_ID" VARCHAR2(20 BYTE), 
	"UPDATE_DATE" TIMESTAMP (6), 
	 CONSTRAINT "ASQ_ZATCA_INVOICE_STAGING_PK" PRIMARY KEY ("ICV", "ORGANIZATION_ID", "INVOICE_ID", "BUSINESS_DATE", "TRANS_SEQ", "WKSTN_ID")
  USING INDEX TABLESPACE &dbIndexTableSpace.
  ) 
  TABLESPACE &dbDataTableSpace.;