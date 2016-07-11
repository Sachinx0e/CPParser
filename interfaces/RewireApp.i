HEADER_FILE := application\RewireApp.h
PARENT_FILE := Application.h

MEMOWN := static RewireApp* getInstance();

FUNCTION_IGNORE := virtual Applib::Databases::Database* createDatabase(std::string dbName);
FUNCTION_IGNORE := Applib::Databases::Database* getDatabase();

IMPORT_SOURCE := StringUtils.h