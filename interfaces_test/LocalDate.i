HEADER_FILE := LocalDate.h

CONSTRUCTOR_IGNORE := LocalDate(const LocalDate& date);
CONSTRUCTOR_IGNORE := LocalDate(long millis);
CONSTRUCTOR_IGNORE := LocalDate(bool isValid);

FUNCTION_IGNORE := LocalDate& operator= (const LocalDate& rDate);
FUNCTION_IGNORE := bool operator!=(LocalDate& rDate);
FUNCTION_IGNORE := bool operator>=(LocalDate& rDate);
FUNCTION_IGNORE := bool operator<=(LocalDate& rDate);
FUNCTION_IGNORE := bool operator==(const LocalDate& rDate) const;
FUNCTION_IGNORE := bool operator!=(const LocalDate& rDate) const;
FUNCTION_IGNORE := bool operator>(const LocalDate& rDate) const;
FUNCTION_IGNORE := bool operator<(const LocalDate& rDate) const;
FUNCTION_IGNORE := bool operator>=(const LocalDate& rDate) const;
FUNCTION_IGNORE := bool operator<=(const LocalDate& rDate) const;

RENAME_FUNC : operator== : equals
RENAME_FUNC : operator> : isAfter
RENAME_FUNC : operator< : isBefore

IMPORT := headers\DurationDateWrapper.h