package org.tadamski.examples.js;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Foo {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 25)
    @Pattern(regexp = "[A-Z][a-z]{2,}", message = "Must start with big letter, and has at least 3 letters")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
