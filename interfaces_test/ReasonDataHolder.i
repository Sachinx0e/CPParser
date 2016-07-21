HEADER_FILE := reasons\ReasonDataHolder.h
PARENT_FILE := DataHolder.h := true

MEMOWN := virtual typeT* getRef(long position);

CONSTRUCTOR_IGNORE := ReasonDataHolder(std::vector<Reason*>* reasons, ReasonFilter* filter);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\ReasonsWrapper.h

IMPORT_SOURCE := StringUtils.h