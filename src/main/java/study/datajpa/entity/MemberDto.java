package study.datajpa.entity;

public class MemberDto {

    private Long id;
    private String name;
    private int age;


    public MemberDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
