package com.lawencon.jobportalspringboot.spesification;


import org.springframework.data.jpa.domain.Specification;

import com.lawencon.jobportalspringboot.persistance.entity.User;

public class UserSpecification {
  public static Specification<User> hasName(String username) {
    return (root, query, criteriaBuilder) -> {
      if (username == null) {
        return criteriaBuilder.conjunction();
      }
      String pattern = "%" + username.toLowerCase() + "%";
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), pattern);
    };
  }

  public static Specification<User> hasEmail(String email) {
    return (root, query, criteriaBuilder) -> {
      if (email == null) {
        return criteriaBuilder.conjunction();
      }
      String pattern = "%" + email.toLowerCase() + "%";
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern);
    };
  }


  // public static Specification hasAge(Integer age) {
  //   return (root, query, criteriaBuilder) ->
  //       age == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("age"), age);
  // }
}