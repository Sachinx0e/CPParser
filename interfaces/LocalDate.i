HEADER_FILE : LocalDate.h
CONVERSION_CONSTRUCTOR : true

FUNCTIONS_IGNORE_START

LocalDate(long millis);
LocalDate(bool isValid);
bool operator!=(LocalDate& rDate);
bool operator>=(LocalDate& rDate);
bool operator<=(LocalDate& rDate);

FUNCTIONS_IGNORE_END

RENAME_FUNC : operator== : equals
RENAME_FUNC : operator> : isAfter
RENAME_FUNC : operator< : isBefore


IMPORTS_START

headers\DurationDateWrapper.h

IMPORTS_END
