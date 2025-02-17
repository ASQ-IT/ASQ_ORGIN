-- ***************************************************************************
-- Place any customer data that must be incorporated into the build into this file
--
-- All sql statements that goes here should be defined in a update-safe way, ie. the final script should be able 
-- to run against the database any number of times and should produce the same output.
--
-- This file should finish with a database commit; at least at the end of the file.
-- 
-- ***************************************************************************

-- ***************************************************************************
-- Oracle db specific command. Remove or comment this line out for all other DBMS.
ALTER SESSION SET CURRENT_SCHEMA=$(DbSchema);
-- ***************************************************************************

-- *******************************************
-- Default Employee
-- *******************************************
DELETE FROM CRM_PARTY WHERE organization_id = $(OrgID) AND party_id = 0;
INSERT INTO crm_party (organization_id, party_id, employee_id, last_name, party_typcode, create_date, create_user_id, update_date, update_user_id) 
  VALUES ($(OrgID) , 0, 0, 'ASQ SYSTEM USER', 'SYSTEM', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM crm_party_locale_information WHERE organization_id = $(OrgID) AND party_id = 0;
INSERT INTO crm_party_locale_information (organization_id, party_id, party_locale_seq, primary_flag, address1, city, state, postal_code, country, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 0, 2, 1, '30500 Bruce Industrial Pkwy', 'Solon', 'OH', '44139', 'US', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO crm_party_locale_information (organization_id, party_id, party_locale_seq, primary_flag, address1, city, state, postal_code, country, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 0, 3, 0, '353 Oxford St', 'London', 'England', 'SW1A 1AA', 'UK', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM crm_party_telephone WHERE organization_id = $(OrgID) AND party_id = 0 AND telephone_type in ('Home', 'HOME');
INSERT INTO crm_party_telephone (organization_id, party_id, telephone_type, telephone_number, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , 0, 'HOME', '+966 126 599 569', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM hrs_employee WHERE organization_id = $(OrgID) AND employee_id = '0';
INSERT INTO hrs_employee (organization_id, employee_id, login_id, party_id, job_title, employee_role_code, group_membership,
      primary_group, employee_typcode, training_status_enum,  employee_statcode, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , '0', '0', 0, 'ASQ SYSTEM USER', 'EVERYONE', 'AQ==', 'EVERYONE', 'EVERYONE', 'EXEMPT', 'A', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

-- *******************************************
-- Default Employee 100
-- *******************************************
DELETE FROM CRM_PARTY WHERE organization_id = $(OrgID) AND party_id = 100;
INSERT INTO crm_party (organization_id, party_id, employee_id, last_name, party_typcode, create_date, create_user_id, update_date, update_user_id) 
  VALUES ($(OrgID) , 100, 100, 'ASQ LOGIN USER', 'SYSTEM', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM crm_party_locale_information WHERE organization_id = $(OrgID) AND party_id = 100 AND party_locale_seq = 2;
INSERT INTO crm_party_locale_information (organization_id, party_id, party_locale_seq, primary_flag, address1, city, state, postal_code, country, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , 100, 2, 1, 'ASQ LOGIN USER ', 'Jeddah', 'Makkah', '21955', 'SA', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM crm_party_telephone WHERE organization_id = $(OrgID) AND party_id = 100 AND telephone_type in ('Home', 'HOME');
INSERT INTO crm_party_telephone (organization_id, party_id, telephone_type, telephone_number, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , 100, 'HOME', '+966 126 599 569', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM hrs_employee WHERE organization_id = $(OrgID) AND employee_id = '100';
INSERT INTO hrs_employee (organization_id, employee_id, login_id, party_id, job_title, employee_role_code, group_membership,
      primary_group, employee_typcode, training_status_enum,  employee_statcode, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , '100', '100', 100, 'ASQ LOGIN USER', 'EVERYONE', 'AQ==', 'EVERYONE', 'EVERYONE', 'EXEMPT', 'A', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
  
DELETE FROM hrs_employee_password WHERE organization_id = $(OrgID) AND employee_id = '100';
INSERT INTO hrs_employee_password (organization_id, employee_id, password, effective_date, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , '100', '{SHA512}a8f51fd86e36fa6b49da51438cc2fb3051b3f86323f6ab043ef67ef713d1a0e5f8cd95ecbbbae65000bcf3d0f8cfe11e54d764ed413a6f5314c6ad9fcc5f0400$d462287e74820bed$100000', GETUTCDATE(), GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE FROM hrs_employee_store WHERE organization_id = $(OrgID) AND employee_id = '100';
INSERT INTO hrs_employee_store (organization_id, rtl_loc_id, employee_id, employee_store_seq, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , $(StoreID), '100', 0, GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

-- *******************************************
-- Tender Type code
-- *******************************************
DELETE FROM loc_wkstn WHERE organization_id = $(OrgID) AND rtl_loc_id = '$(StoreID)';  
INSERT INTO loc_wkstn (organization_id, rtl_loc_id, wkstn_id, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , $(StoreID), 1, GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

-- *******************************************
-- Tender Type code
-- *******************************************
DELETE FROM TND_TNDR_TYPCODE WHERE organization_id = $(OrgID);

INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'ACCOUNT', 'Account', 60, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'ACCOUNT_CREDIT', 'Account Credit', 10, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CURRENCY', 'Currency', 0, 'DENOMINATION', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CREDIT_CARD', 'Credit Card', 20, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MISCELLANEOUS', 'Miscellaneous', 40, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MISCELLANEOUS_VOUCHER', 'Miscellaneous Voucher', 45, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'VOUCHER', 'Voucher', 80, 'TOTAL_SHORT', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'DEBIT_CARD', 'Debit Card', 100, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'PAY_BY_LINK', 'Pay By Link', 50, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'LOYALITY', 'Loyality', 20, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MISCELLANEOUS_EFTLINK', 'Miscellaneous EFTLink Tender', 140, 'TOTAL_NORMAL', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO tnd_tndr_typcode (organization_id, tndr_typcode, description, sort_order, unit_count_req_code, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'COUPON', 'Coupon', 30, 'TOTAL_SHORT', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');


-- *************************
-- Session Store Data
-- *************************
DELETE FROM TSN_TNDR_REPOSITORY 
  WHERE organization_id = $(OrgID) AND 
        rtl_loc_id = $(StoreID) AND 
        tndr_repository_id IN ('STOREBANK', 'DEPOSIT_BANK','001');

INSERT INTO tsn_tndr_repository (organization_id, tndr_repository_id, rtl_loc_id, typcode, 
      not_issuable_flag, name, dflt_wkstn_id, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , 'STOREBANK', $(StoreID), 'STOREBANK', 1, 'Store Safe', NULL, GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

INSERT INTO tsn_tndr_repository (organization_id, tndr_repository_id, rtl_loc_id, typcode, 
      not_issuable_flag, name, description, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , 'DEPOSIT_BANK', $(StoreID), 'BANK', 1, 'Deposit Bank', '+TSN_TNDR_REPOSITORY_DEPOSIT_BANK', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

INSERT INTO tsn_tndr_repository (organization_id, tndr_repository_id, rtl_loc_id, typcode, 
      not_issuable_flag, name, dflt_wkstn_id, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID) , '001', $(StoreID), 'TILL', 0, 'ASQ Default Registor 001', 1, GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
   
DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+TSN_TNDR_REPOSITORY_DEPOSIT_BANK';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+TSN_TNDR_REPOSITORY_DEPOSIT_BANK', 'DEFAULT', '*', '*', 'Deposit Bank', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
  
-- *******************************************
-- Store safe session
-- *******************************************
DELETE FROM tsn_session WHERE organization_id = $(OrgID) AND rtl_loc_id = $(StoreID) AND session_id = 0;
INSERT INTO tsn_session (organization_id, rtl_loc_id, session_id, employee_party_id, statcode, tndr_repository_id, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), $(StoreID), 0, NULL, 'ENDCOUNT', 'STOREBANK', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
  

-- *******************************************
-- Security & Privilege Data
-- *******************************************
DELETE FROM sec_groups
  WHERE organization_id = $(OrgID) AND
        group_id IN ('EVERYONE', 'TRAINEE', 'CASHIER', 'KEYHOLDER', 'MANAGER');

INSERT INTO sec_groups (organization_id, group_id, description, bitmap_position, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , 'EVERYONE', '+SEC_GROUPS_EVERYONE', 1, GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_groups (organization_id, group_id, description, bitmap_position, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , 'TRAINEE', '+SEC_GROUPS_TRAINEE', 2, GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_groups (organization_id, group_id, description, bitmap_position, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , 'CASHIER', '+SEC_GROUPS_CASHIER', 3, GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_groups (organization_id, group_id, description, bitmap_position, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , 'KEYHOLDER', '+SEC_GROUPS_KEYHOLDER', 4, GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_groups (organization_id, group_id, description, bitmap_position, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID) , 'MANAGER', '+SEC_GROUPS_MANAGER', 5, GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');

DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+SEC_GROUPS_EVERYONE';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+SEC_GROUPS_EVERYONE', 'DEFAULT', '*', '*', 'EVERYONE', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+SEC_GROUPS_TRAINEE';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+SEC_GROUPS_TRAINEE', 'DEFAULT', '*', '*', 'TRAINEE', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+SEC_GROUPS_CASHIER';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+SEC_GROUPS_CASHIER', 'DEFAULT', '*', '*', 'CASHIER', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+SEC_GROUPS_KEYHOLDER';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+SEC_GROUPS_KEYHOLDER', 'DEFAULT', '*', '*', 'KEYHOLDER', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
DELETE FROM com_translations WHERE organization_id = $(OrgID) AND translation_key='+SEC_GROUPS_MANAGER';
INSERT INTO com_translations (organization_id, translation_key, locale, org_code, org_value, translation, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), '+SEC_GROUPS_MANAGER', 'DEFAULT', '*', '*', 'MANAGER', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');

DELETE FROM sec_group_roles
  WHERE organization_id = $(OrgID) AND
        group_id IN ('EVERYONE', 'TRAINEE', 'CASHIER', 'KEYHOLDER', 'MANAGER');

INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVERYONE', 'DISCOUNTSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'TRAINEE', 'DISCOUNTSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'DISCOUNTSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'KEYHOLDER', 'DISCOUNTSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MANAGER', 'DISCOUNTSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVERYONE', 'ITEMSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'TRAINEE', 'ITEMSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'ITEMSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'KEYHOLDER', 'ITEMSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MANAGER', 'ITEMSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVERYONE', 'PRICESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'TRAINEE', 'PRICESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'PRICESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'KEYHOLDER', 'PRICESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MANAGER', 'PRICESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVERYONE', 'TAXESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'TRAINEE', 'TAXESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'TAXESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'KEYHOLDER', 'TAXESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MANAGER', 'TAXESSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVERYONE', 'TENDERSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'TRAINEE', 'TENDERSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'TENDERSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'KEYHOLDER', 'TENDERSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');
INSERT INTO sec_group_roles (organization_id, group_id, role, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'MANAGER', 'TENDERSSERVICERESOURCE_READ', GETUTCDATE(), 'ASQBATA', GETUTCDATE(), 'ASQBATA');

DELETE FROM SEC_PASSWORD WHERE organization_id = $(OrgID) AND
        PASSWORD IN ('LLLFMGZ4PZYPLQJ4TCPVKJTGYLKDXAM47IIMKXJPK6B7PGET5FW4CVRVZ4BCIPWYSNRXVQSXM5UUOUY7GB6E5XPL6JOAMQKU6H5Q6OOWWWYHDDEW4IUL367D2ZN7A7WU2EUHYAIQV3IPRSUD7VQMCFPGK4NKEZ5DT737KK7WGTEVUD52OFTPWO34UOJFAWY55SLD3XQLCJWK5CNLYDLSJJPPR53PHMZZQ37MNVQJ3O7HKMXW');
Insert into SEC_PASSWORD (ORGANIZATION_ID,PASSWORD_ID,PASSWORD,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),0,'LLLFMGZ4PZYPLQJ4TCPVKJTGYLKDXAM47IIMKXJPK6B7PGET5FW4CVRVZ4BCIPWYSNRXVQSXM5UUOUY7GB6E5XPL6JOAMQKU6H5Q6OOWWWYHDDEW4IUL367D2ZN7A7WU2EUHYAIQV3IPRSUD7VQMCFPGK4NKEZ5DT737KK7WGTEVUD52OFTPWO34UOJFAWY55SLD3XQLCJWK5CNLYDLSJJPPR53PHMZZQ37MNVQJ3O7HKMXW','SYSTEM',GETUTCDATE(),'SYSTEM',GETUTCDATE(),null);

DELETE FROM hrs_work_codes
  WHERE organization_id = $(OrgID) AND
        work_code IN ('ADMIN', 'DEMO', 'EVENT', 'HOLIDAY_SALES', 'INVENTORY', 'LUNCH_BREAK', 'SALES', 'CASHIER', 'UTILITY');

INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'ADMIN', 'ADMIN', 40, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'DEMO', 'DEMO', 50, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'EVENT', 'EVENT', 60, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'HOLIDAY_SALES', 'HOLIDAY_SALES', 70, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'INVENTORY', 'INVENTORY', 30, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'LUNCH_BREAK', 'LUNCH_BREAK', 20, 'ADMINISTRATIVE_WORK_CODE', 0, 'OTHER', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'SALES', 'SALES', 10, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'CASHIER', 'CASHIER', 10, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');
INSERT INTO hrs_work_codes (organization_id, work_code, description, sort_order, privilege, selling_flag, payroll_category, create_date, create_user_id, update_date, update_user_id) VALUES ($(OrgID), 'UTILITY', 'UTILITY', 70, 'ADMINISTRATIVE_WORK_CODE', 0, 'REGULAR', GETUTCDATE(), 'ASQBDATA', GETUTCDATE(), 'ASQBDATA');

DELETE from SEC_ACL where ORGANIZATION_ID=$(OrgID) and SECURED_OBJECT_ID in ('CUSTOMER_ATTRIBUTES','CUSTOMER_CONTACT_INFO', 'CUSTOMER_EMPLOYEE_DATA', 'CUSTOMER_FINANCIAL_INFO', 'CUSTOMER_GROUPS', 'CUSTOMER_ID','DEFAULT', 'EMPLOYEE_DATA', 'EMPLOYEE_LOCKOUT','EMPLOYEE_NOTE');
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ATTRIBUTES',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_CONTACT_INFO',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_EMPLOYEE_DATA',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_FINANCIAL_INFO',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_GROUPS',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ID',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'DEFAULT',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_DATA',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_LOCKOUT',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACL (ORGANIZATION_ID,SECURED_OBJECT_ID,AUTHENTICATION_REQ_FLAG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_NOTE',0,'ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);

DELETE from SEC_ACCESS_TYPES where ORGANIZATION_ID= $(OrgID) and SECURED_OBJECT_ID in ('CUSTOMER_ATTRIBUTES','CUSTOMER_CONTACT_INFO', 'CUSTOMER_EMPLOYEE_DATA', 'CUSTOMER_FINANCIAL_INFO', 'CUSTOMER_GROUPS', 'CUSTOMER_ID','DEFAULT', 'EMPLOYEE_DATA', 'EMPLOYEE_LOCKOUT','EMPLOYEE_NOTE','CUSTOMER_FINANCIAL_INFO');
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ATTRIBUTES','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ATTRIBUTES','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ATTRIBUTES','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_CONTACT_INFO','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_CONTACT_INFO','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_CONTACT_INFO','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_EMPLOYEE_DATA','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_EMPLOYEE_DATA','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_EMPLOYEE_DATA','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_FINANCIAL_INFO','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_FINANCIAL_INFO','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_GROUPS','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_GROUPS','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_GROUPS','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ID','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ID','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_ID','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'DEFAULT','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'DEFAULT','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'DEFAULT','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_DATA','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_DATA','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_DATA','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_LOCKOUT','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_LOCKOUT','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_LOCKOUT','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_NOTE','CREATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_NOTE','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'EMPLOYEE_NOTE','UPDATE','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into SEC_ACCESS_TYPES (ORGANIZATION_ID,SECURED_OBJECT_ID,ACCESS_TYPCODE,GROUP_MEMBERSHIP,NO_ACCESS_SETTINGS,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'CUSTOMER_FINANCIAL_INFO','READ','AQ==','HIDDEN','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);

---##########--
-- Planet X Tranasction amount threshold 
---##########--
DELETE FROM LOC_WKSTN_P WHERE organization_id = $(OrgID) and PROPERTY_CODE ='TF_PL_MINIMUM_AMOUNT' ;
Insert into LOC_WKSTN_P (ORGANIZATION_ID,RTL_LOC_ID,WKSTN_ID,PROPERTY_CODE,TYPE,STRING_VALUE,DATE_VALUE,DECIMAL_VALUE,CREATE_DATE,CREATE_USER_ID,UPDATE_DATE,UPDATE_USER_ID,RECORD_STATE) values ($(OrgID),$(StoreID),1,'TF_PL_MINIMUM_AMOUNT','DECIMAL',null,null,10,null,null,null,null,null);

---##########--
DELETE FROM itm_merch_hierarchy_levels WHERE organization_id = $(OrgID);
  
Insert into ITM_MERCH_HIERARCHY_LEVELS (ORGANIZATION_ID,LEVEL_ID,LEVEL_CODE,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),1,'DEPARTMENT','+MERCHLEVEL1','BASEDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into ITM_MERCH_HIERARCHY_LEVELS (ORGANIZATION_ID,LEVEL_ID,LEVEL_CODE,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),2,'SUBDEPARTMENT','+MERCHLEVEL2','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into ITM_MERCH_HIERARCHY_LEVELS (ORGANIZATION_ID,LEVEL_ID,LEVEL_CODE,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),3,'CLASS','+MERCHLEVEL3','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);
Insert into ITM_MERCH_HIERARCHY_LEVELS (ORGANIZATION_ID,LEVEL_ID,LEVEL_CODE,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),4,'SUBCLASS','+MERCHLEVEL4','ASQBDATA',GETUTCDATE(),'ASQBDATA',GETUTCDATE(),null);

--####################################
--MASS
DELETE FROM com_measurement WHERE organization_id = $(OrgID) AND dimension IN ('MASS') AND code IN ('CAR','CG','CGM','CKG','CTN','D','G','GM','GVW','KG','KSB','KTS','LB','LBS','MG','OSG','OZ','T','TON','TS');

INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'CAR', '_fds.unitOfMeasure.CAR.name', '_fds.unitOfMeasure.CAR.symbol', 0.2, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'CG', '_fds.unitOfMeasure.CG.name', '_fds.unitOfMeasure.CG.symbol', 0.01, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'CGM', '_fds.unitOfMeasure.CGM.name', '_fds.unitOfMeasure.CGM.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'CKG', '_fds.unitOfMeasure.CKG.name', '_fds.unitOfMeasure.CKG.symbol', 1000, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'CTN', '_fds.unitOfMeasure.CTN.name', '_fds.unitOfMeasure.CTN.symbol', 1000000, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'D', '_fds.unitOfMeasure.D.name', '_fds.unitOfMeasure.D.symbol', 1.275, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'G', '_fds.unitOfMeasure.G.name', '_fds.unitOfMeasure.G.symbol', 1, 3, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'GM', '_fds.unitOfMeasure.GM.name', '_fds.unitOfMeasure.GM.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'GVW', '_fds.unitOfMeasure.GVW.name', '_fds.unitOfMeasure.GVW.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'KG', '_fds.unitOfMeasure.KG.name', '_fds.unitOfMeasure.KG.symbol', 1000, 3, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'KSB', '_fds.unitOfMeasure.KSB.name', '_fds.unitOfMeasure.KSB.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'KTS', '_fds.unitOfMeasure.KTS.name', '_fds.unitOfMeasure.KTS.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'LB', '_fds.unitOfMeasure.LB.name', '_fds.unitOfMeasure.LB.symbol', 453.592, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'LBS', '_fds.unitOfMeasure.LBS.name', '_fds.unitOfMeasure.LBS.symbol', 453.592, 3, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'MG', '_fds.unitOfMeasure.MG.name', '_fds.unitOfMeasure.MG.symbol', 0.001, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'OSG', '_fds.unitOfMeasure.OSG.name', '_fds.unitOfMeasure.OSG.symbol', 1, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'OZ', '_fds.unitOfMeasure.OZ.name', '_fds.unitOfMeasure.OZ.symbol', 28.3495, 3, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'T', '_fds.unitOfMeasure.T.name', '_fds.unitOfMeasure.T.symbol', 1000000, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'TON', '_fds.unitOfMeasure.TON.name', '_fds.unitOfMeasure.TON.symbol', 907185, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');
INSERT INTO com_measurement (organization_id, dimension, code, name, symbol, factor, qty_scale, create_date, create_user_id, update_date, update_user_id)
  VALUES ($(OrgID), 'MASS', 'TS', '_fds.unitOfMeasure.TS.name', '_fds.unitOfMeasure.TS.symbol', 907185, NULL, GETUTCDATE(), 'BASEDATA', GETUTCDATE(), 'BASEDATA');

--####################################  
delete from COM_MEASUREMENT where DIMENSION in ('TOLA');
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','TOLA','_fds.unitOfMeasure.TOLA.name','_fds.unitOfMeasure.TOLA.symbol',12,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','HTOLA','_fds.unitOfMeasure.HTOLA.name','_fds.unitOfMeasure.HTOLA.symbol',6,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','QTOLA','_fds.unitOfMeasure.QTOLA.name','_fds.unitOfMeasure.QTOLA.symbol',3,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);

Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','2TOLA','_fds.unitOfMeasure.2TOLA.name','_fds.unitOfMeasure.2TOLA.symbol',24,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','3TOLA','_fds.unitOfMeasure.3TOLA.name','_fds.unitOfMeasure.3TOLA.symbol',36,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','5TOLA','_fds.unitOfMeasure.5TOLA.name','_fds.unitOfMeasure.5TOLA.symbol',60,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into COM_MEASUREMENT (ORGANIZATION_ID,DIMENSION,CODE,NAME,SYMBOL,FACTOR,QTY_SCALE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'TOLA','10TOLA','_fds.unitOfMeasure.10TOLA.name','_fds.unitOfMeasure.10TOLA.symbol',120,0,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);

--####################################
delete ITM_ITEM_DIMENSION_VALUE where DIMENSION in ('TOLA');

Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','HTOLA','*','*',20,'Half Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','TOLA','*','*',10,'One Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','QTOLA','*','*',30,'Quator Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);

Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','2TOLA','*','*',40,'Two Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','3TOLA','*','*',50,'Three Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','5TOLA','*','*',60,'Five Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into ITM_ITEM_DIMENSION_VALUE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,VALUE,ORG_CODE,ORG_VALUE,SORT_ORDER,DESCRIPTION,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','10TOLA','*','*',70,'Ten Tola','BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);

--####################################
delete ITM_ITEM_DIMENSION_TYPE where DIMENSION_SYSTEM in ('ASQ_TOLA');
Insert into ITM_ITEM_DIMENSION_TYPE (ORGANIZATION_ID,DIMENSION_SYSTEM,DIMENSION,ORG_CODE,ORG_VALUE,SEQ,SORT_ORDER,DESCRIPTION,PROMPT_MSG,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID) ,'ASQ_TOLA','TOLA','*','*',1,10,'+ASQ_TOLA_DESC',null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);

----########
---- Zatca Staging Intial Record  ---
----########
delete ASQ_ZATCA_INVOICE_STAGING where ORGANIZATION_ID in ($(OrgID));
Insert into ASQ_ZATCA_INVOICE_STAGING (ORGANIZATION_ID,BUSINESS_DATE,TRANS_SEQ,WKSTN_ID,STATUS,ICV,INVOICE_ID,INVOICE_DATE,INVOICE_HASHCODE) values ($(OrgID),GETUTCDATE(),1,1,'IGNORE',4,'17254836000001134716',GETUTCDATE(),'NWZlY2ViNjZmZmM4NmYzOGQ5NTI3ODZjNmQ2OTZjNzljMmRiYzIzOWRkNGU5MWI0NjcyOWQ3M2EyN2ZiNTdlOQ==');

---###################################################################
delete THR_PAYROLL_CATEGORY where ORGANIZATION_ID in ($(OrgID));

Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'BEREAVEMENT','_payrollCategory.BEREAVEMENT.description',80,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'DT','_payrollCategory.DT.description',25,1,1,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'FUNERAL','_payrollCategory.FUNERAL.description',100,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'HOLIDAY','_payrollCategory.HOLIDAY.description',70,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'JURY','_payrollCategory.JURY.description',90,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'OT','_payrollCategory.OT.description',20,1,1,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'OTHER','_payrollCategory.OTHER.description',60,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'PERSONAL','_payrollCategory.PERSONAL.description',50,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'REGULAR','_payrollCategory.REGULAR.description',10,1,1,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'SICK','_payrollCategory.SICK.description',40,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);
Insert into THR_PAYROLL_CATEGORY (ORGANIZATION_ID,PAYROLL_CATEGORY,DESCRIPTION,SORT_ORDER,INCLUDE_IN_OVERTIME_FLAG,WORKING_CATEGORY_FLAG,PAY_CODE,CREATE_USER_ID,CREATE_DATE,UPDATE_USER_ID,UPDATE_DATE,RECORD_STATE) values ($(OrgID),'VACATION','_payrollCategory.VACATION.description',30,1,0,null,'BASEDATA',GETUTCDATE(),'BASEDATA',GETUTCDATE(),null);



