package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Order;

import java.util.Map;

@Component
public class OrderFilter {
    private UtilFilter utilFilter;

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    private Specification<Order> spec;
    private StringBuilder filterDefinition;

    public void init(Map<String, String> params) {
        //инициируем настройки фильтра №страницы-лимита-направления
        utilFilter.init(params);
        //инициируем нулевую спецификацию фильтра(фильтр не применится)
        this.spec = Specification.where(null);
        //инициируем объект билдера строки для сборки строки с параметрами фильтра,
        // добавляемыми к запросу
        this.filterDefinition = utilFilter.getFilterDefinition();
    }

    public Specification<Order> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
