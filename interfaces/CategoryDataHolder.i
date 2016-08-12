HEADER_FILE := categories\CategoryDataHolder.h
PARENT_FILE := DataHolder.h := true

MEMOWN := Category* getRef(int position);

CONSTRUCTOR_IGNORE := CategoryDataHolder(std::vector<Category*>* categories, CategoryFilter* filter);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\CategoryWrapper.h

IMPORT_SOURCE := StringUtils.h