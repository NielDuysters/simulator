package be.kdg.sa.simulator.domain;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class LineTypeConverter extends AbstractBeanField {

    @Override
    protected LineType convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return LineType.valueOf(value);
    }
}
