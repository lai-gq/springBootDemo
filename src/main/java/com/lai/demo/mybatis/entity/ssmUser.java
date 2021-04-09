package com.lai.demo.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
/*
    使用Lombok，通过添加注解的方式，不需要为类编写getter或eques方法
    常用注解：
        @Setter 注解在类或字段，注解在类时为所有字段生成setter方法，注解在字段上时只为该字段生成setter方法。
        @Getter 使用方法同上，区别在于生成的是getter方法。
        @ToString 注解在类，添加toString方法。
        @EqualsAndHashCode 注解在类，生成hashCode和equals方法。
        @NoArgsConstructor 注解在类，生成无参的构造方法。
        @RequiredArgsConstructor 注解在类，为类中需要特殊处理的字段生成构造方法，比如final和被@NonNull注解的字段。
        @AllArgsConstructor 注解在类，生成包含类中所有字段的构造方法。
        @Data 注解在类，生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
            相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集。
        @Slf4j 注解在类，生成log变量，严格意义来说是常量。private static final Logger log = LoggerFactory.getLogger(UserController.class);
        @EqualsAndHashCode 生成equals(Object other) 和 hashCode()方法，且不会使用父类的属性
*/



@Data
@EqualsAndHashCode(callSuper = false) //https://blog.csdn.net/zhanlanmg/article/details/50392266
@Accessors(chain = true) //https://www.wandouip.com/t5i99147/
@Entity
public class ssmUser implements Serializable{
	@Id //表主键
	@GeneratedValue //自增长
    private Long userid;//     用户id
    private String username;//       用户名
    private String password;//       密码
    private String mobile;//           手机号
    private String sex;//            性别（1男2女）
    private Date createTime;//       注册时间
    private String status;//         用户状态(1有效0无效)
    private String remark;//         用户备注
    private Date lastLoginTime;//    最近登录时间
    private Long loginCount;// 登录次数
    private byte[] head;//           头像
    
	/*public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }*/
}