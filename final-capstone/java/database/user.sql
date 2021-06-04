-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER chatbotdb_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO chatbotdb_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO chatbotdb_owner;

CREATE USER chatbotdb_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO chatbotdb_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO chatbotdb_appuser;


