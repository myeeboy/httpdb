package com.hg.httpdb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库元数据
 * @author wanghg
 */
public class HttpDBMetaData implements DatabaseMetaData {
	private HttpConnection conn;
	private Map metas = new HashMap();

	public HttpDBMetaData(HttpConnection conn) throws SQLException {
		this.conn = conn;
		RowSet rowSet = ((HttpResultSet) new HttpStatement(this.conn).executeQuery("$meta$")).getRowSet();
		for (int i = 0; i < rowSet.size(); i++) {
			metas.put(rowSet.get(i).get("KEY"), rowSet.get(i).get("VALUE"));
		}
	}
    public boolean allProceduresAreCallable() throws SQLException {
        return true;
    }
    public boolean allTablesAreSelectable() throws SQLException {
        return true;
    }
    public String getURL() throws SQLException {
        return this.conn.getUrl();
    }
    public String getUserName() throws SQLException {
        return this.conn.getUser();
    }
    public boolean isReadOnly() throws SQLException {
        return false;
    }
    public boolean nullsAreSortedHigh() throws SQLException {
        return true;
    }
    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }
    public boolean nullsAreSortedAtStart() throws SQLException {
        return true;
    }
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }
    public String getDatabaseProductName() throws SQLException {
        return To.toString(this.metas.get("DatabaseProductName"));
    }
    public String getDatabaseProductVersion() throws SQLException {
        return To.toString(this.metas.get("DatabaseProductVersion"));
    }
    public String getDriverName() throws SQLException {
        return "HttpDB Driver [" + To.toString(this.metas.get("DriverName")) + "]";
    }
    public String getDriverVersion() throws SQLException {
        return "0.1 [" + To.toString(this.metas.get("DriverVersion")) + "]";
    }
    public int getDriverMajorVersion() {
        return 0;
    }
    public int getDriverMinorVersion() {
        return 1;
    }
    public boolean usesLocalFiles() throws SQLException {
        return true;
    }
    public boolean usesLocalFilePerTable() throws SQLException {
        return true;
    }
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }
    public String getIdentifierQuoteString() throws SQLException {
        return "'";
    }
    public String getSQLKeywords() throws SQLException {
    	return "";
    }
    public String getNumericFunctions() throws SQLException {
        return "";
    }
    public String getStringFunctions() throws SQLException {
        return "";
    }
    public String getSystemFunctions() throws SQLException {
        return "";
    }
    public String getTimeDateFunctions() throws SQLException {
        return "";
    }
    public String getSearchStringEscape() throws SQLException {
        return "";
    }
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsAlterTableWithAddColumn()
     */
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsAlterTableWithDropColumn()
     */
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsColumnAliasing()
     */
    public boolean supportsColumnAliasing() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#nullPlusNonNullIsNull()
     */
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsConvert()
     */
    public boolean supportsConvert() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsConvert(int, int)
     */
    public boolean supportsConvert(int fromType, int toType)
            throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsTableCorrelationNames()
     */
    public boolean supportsTableCorrelationNames() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsDifferentTableCorrelationNames()
     */
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsExpressionsInOrderBy()
     */
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOrderByUnrelated()
     */
    public boolean supportsOrderByUnrelated() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsGroupBy()
     */
    public boolean supportsGroupBy() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsGroupByUnrelated()
     */
    public boolean supportsGroupByUnrelated() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsGroupByBeyondSelect()
     */
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsLikeEscapeClause()
     */
    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsMultipleResultSets()
     */
    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsMultipleTransactions()
     */
    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsNonNullableColumns()
     */
    public boolean supportsNonNullableColumns() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsMinimumSQLGrammar()
     */
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCoreSQLGrammar()
     */
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsExtendedSQLGrammar()
     */
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsANSI92EntryLevelSQL()
     */
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsANSI92IntermediateSQL()
     */
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsANSI92FullSQL()
     */
    public boolean supportsANSI92FullSQL() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsIntegrityEnhancementFacility()
     */
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOuterJoins()
     */
    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsFullOuterJoins()
     */
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsLimitedOuterJoins()
     */
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getSchemaTerm()
     */
    public String getSchemaTerm() throws SQLException {
        return "";
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getProcedureTerm()
     */
    public String getProcedureTerm() throws SQLException {
        return "";
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getCatalogTerm()
     */
    public String getCatalogTerm() throws SQLException {
        return "";
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#isCatalogAtStart()
     */
    public boolean isCatalogAtStart() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getCatalogSeparator()
     */
    public String getCatalogSeparator() throws SQLException {
        return "";
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSchemasInDataManipulation()
     */
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSchemasInProcedureCalls()
     */
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSchemasInTableDefinitions()
     */
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return To.toBool(this.metas.get("supportsSchemasInTableDefinitions"));
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSchemasInIndexDefinitions()
     */
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSchemasInPrivilegeDefinitions()
     */
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCatalogsInDataManipulation()
     */
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCatalogsInProcedureCalls()
     */
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCatalogsInTableDefinitions()
     */
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCatalogsInIndexDefinitions()
     */
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCatalogsInPrivilegeDefinitions()
     */
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsPositionedDelete()
     */
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsPositionedUpdate()
     */
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSelectForUpdate()
     */
    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsStoredProcedures()
     */
    public boolean supportsStoredProcedures() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSubqueriesInComparisons()
     */
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSubqueriesInExists()
     */
    public boolean supportsSubqueriesInExists() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSubqueriesInIns()
     */
    public boolean supportsSubqueriesInIns() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsSubqueriesInQuantifieds()
     */
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsCorrelatedSubqueries()
     */
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsUnion()
     */
    public boolean supportsUnion() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsUnionAll()
     */
    public boolean supportsUnionAll() throws SQLException {
        return true;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOpenCursorsAcrossCommit()
     */
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOpenCursorsAcrossRollback()
     */
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOpenStatementsAcrossCommit()
     */
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#supportsOpenStatementsAcrossRollback()
     */
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxBinaryLiteralLength()
     */
    public int getMaxBinaryLiteralLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxCharLiteralLength()
     */
    public int getMaxCharLiteralLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnNameLength()
     */
    public int getMaxColumnNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnsInGroupBy()
     */
    public int getMaxColumnsInGroupBy() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnsInIndex()
     */
    public int getMaxColumnsInIndex() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnsInOrderBy()
     */
    public int getMaxColumnsInOrderBy() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnsInSelect()
     */
    public int getMaxColumnsInSelect() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxColumnsInTable()
     */
    public int getMaxColumnsInTable() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxConnections()
     */
    public int getMaxConnections() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxCursorNameLength()
     */
    public int getMaxCursorNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxIndexLength()
     */
    public int getMaxIndexLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxSchemaNameLength()
     */
    public int getMaxSchemaNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxProcedureNameLength()
     */
    public int getMaxProcedureNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxCatalogNameLength()
     */
    public int getMaxCatalogNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxRowSize()
     */
    public int getMaxRowSize() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#doesMaxRowSizeIncludeBlobs()
     */
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxStatementLength()
     */
    public int getMaxStatementLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxStatements()
     */
    public int getMaxStatements() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxTableNameLength()
     */
    public int getMaxTableNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxTablesInSelect()
     */
    public int getMaxTablesInSelect() throws SQLException {
        return Integer.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.sql.DatabaseMetaData#getMaxUserNameLength()
     */
    public int getMaxUserNameLength() throws SQLException {
        return Integer.MAX_VALUE;
    }
    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }
    /**
     * 是否支持事务
     */
    public boolean supportsTransactions() throws SQLException {
        return true;
    }
    public boolean supportsTransactionIsolationLevel(int level)
            throws SQLException {
        return false;
    }
    public boolean supportsDataDefinitionAndDataManipulationTransactions()
            throws SQLException {
        return false;
    }
    public boolean supportsDataManipulationTransactionsOnly()
            throws SQLException {
        return false;
    }
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }
    public ResultSet getProcedures(String catalog, String schemaPattern,
            String procedureNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getProcedureColumns(String catalog, String schemaPattern,
            String procedureNamePattern, String columnNamePattern)
            throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getTables(String catalog, String schemaPattern,
            String tableNamePattern, String[] types) throws SQLException {
        return this.conn.query("$tables$," + catalog + "," + schemaPattern);
    }
    public ResultSet getSchemas() throws SQLException {
        return this.conn.query("$schemas$");
    }
    public ResultSet getCatalogs() throws SQLException {
        return this.conn.query("$catalogs$");
    }
    public ResultSet getTableTypes() throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getColumns(String catalog, String schemaPattern,
            String tableNamePattern, String columnNamePattern)
            throws SQLException {
        return this.conn.query("$columns$," + catalog + "," + schemaPattern + "," + tableNamePattern);
    }
    public ResultSet getColumnPrivileges(String catalog, String schema,
            String table, String columnNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getTablePrivileges(String catalog, String schemaPattern,
            String tableNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getBestRowIdentifier(String catalog, String schema,
            String table, int scope, boolean nullable) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getVersionColumns(String catalog, String schema,
            String table) throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getPrimaryKeys(String catalog, String schema, String table)
            throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getImportedKeys(String catalog, String schema, String table)
            throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getExportedKeys(String catalog, String schema, String table)
            throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getCrossReference(String primaryCatalog,
            String primarySchema, String primaryTable, String foreignCatalog,
            String foreignSchema, String foreignTable) throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getTypeInfo() throws SQLException {
    	return new HttpResultSet();
    }
    public ResultSet getIndexInfo(String catalog, String schema, String table,
            boolean unique, boolean approximate) throws SQLException {
    	return new HttpResultSet();
    }
    public boolean supportsResultSetType(int type) throws SQLException {
        return true;
    }
    public boolean supportsResultSetConcurrency(int type, int concurrency)
            throws SQLException {
        return false;
    }
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return false;
    }
    public boolean updatesAreDetected(int type) throws SQLException {
        return false;
    }
    public boolean deletesAreDetected(int type) throws SQLException {
        return false;
    }
    public boolean insertsAreDetected(int type) throws SQLException {
        return false;
    }
    public boolean supportsBatchUpdates() throws SQLException {
        return true;
    }
    public ResultSet getUDTs(String catalog, String schemaPattern,
            String typeNamePattern, int[] types) throws SQLException {
        return new HttpResultSet();
    }
    
    public Connection getConnection() throws SQLException {
        return this.conn;
    }
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }
    public boolean supportsMultipleOpenResults() throws SQLException {
        return false;
    }
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }
    public ResultSet getSuperTypes(String catalog, String schemaPattern,
            String typeNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getSuperTables(String catalog, String schemaPattern,
            String tableNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getAttributes(String catalog, String schemaPattern,
            String typeNamePattern, String attributeNamePattern)
            throws SQLException {
        return new HttpResultSet();
    }
    public boolean supportsResultSetHoldability(int holdability)
            throws SQLException {
        return false;
    }
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }
    public int getDatabaseMajorVersion() throws SQLException {
        return To.toInt(this.metas.get("DatabaseMajorVersion"));
    }
    public int getDatabaseMinorVersion() throws SQLException {
        return To.toInt(this.metas.get("DatabaseMinorVersion"));
    }
    public int getJDBCMajorVersion() throws SQLException {
        return 1;
    }
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }
    public int getSQLStateType() throws SQLException {
        return DatabaseMetaData.sqlStateSQL99;
    }
    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return null;
    }
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        return this.conn.query("$schemas$," + catalog);
    }
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return false;
    }
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }
    public ResultSet getClientInfoProperties() throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        return new HttpResultSet();
    }
    public Object unwrap(Class arg0) throws SQLException {
        return null;
    }
    public boolean isWrapperFor(Class arg0) throws SQLException {
        return false;
    }
}
