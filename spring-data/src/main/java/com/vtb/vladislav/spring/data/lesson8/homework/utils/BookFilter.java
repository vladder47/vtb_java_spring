package com.vtb.vladislav.spring.data.lesson8.homework.utils;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.repositories.specifications.BookSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;


@Getter
public class BookFilter {
    private Specification<Book> spec;
    private final String requestParameters;

    public BookFilter(MultiValueMap<String, String> params) {
        spec = Specification.where(null);
        StringBuilder parameters = new StringBuilder("&");
        if (params.containsKey("genre") && params.get("genre") != null) {
            for (String genre : params.get("genre")) {
                spec = spec.or(BookSpecifications.genreEqual(genre));
                parameters.append("genre=").append(genre).append('&');
            }
        }
        if (params.containsKey("minPrice") && !params.get("minPrice").get(0).equals("")) {
            System.out.println("я тут");
            String minPrice = params.get("minPrice").get(0);
            spec = spec.and(BookSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(minPrice)));
            parameters.append("minPrice=").append(minPrice).append('&');
        }
        if (params.containsKey("maxPrice") && !params.get("maxPrice").get(0).equals("")) {
            System.out.println("maxPrice я тут");
            String maxPrice = params.get("maxPrice").get(0);
            spec = spec.and(BookSpecifications.priceLesserOrEqualsThan(Integer.parseInt(maxPrice)));
            parameters.append("maxPrice=").append(maxPrice).append('&');
        }
        if (params.containsKey("titlePart") && !params.get("titlePart").get(0).equals("")) {
            String titlePart = params.get("titlePart").get(0);
            spec = spec.and(BookSpecifications.titleLike(titlePart));
            parameters.append("titlePart=").append(titlePart).append('&');
        }
        if (params.containsKey("minPublishYear") && !params.get("minPublishYear").get(0).equals("")) {
            String minPublishYear = params.get("minPublishYear").get(0);
            spec = spec.and(BookSpecifications.publishYearGreaterOrEqualsThan(Integer.parseInt(minPublishYear)));
            parameters.append("minPublishYear=").append(minPublishYear).append('&');
        }
        if (params.containsKey("maxPublishYear") && !params.get("maxPublishYear").get(0).equals("")) {
            String maxPublishYear = params.get("maxPublishYear").get(0);
            spec = spec.and(BookSpecifications.publishYearLesserOrEqualsThan(Integer.parseInt(maxPublishYear)));
            parameters.append("maxPublishYear=").append(maxPublishYear).append('&');
        }
        if (!parameters.toString().equals("&")) {
            this.requestParameters = parameters.subSequence(0, parameters.length() - 1).toString();
        } else {
            this.requestParameters = "";
        }
    }
}
