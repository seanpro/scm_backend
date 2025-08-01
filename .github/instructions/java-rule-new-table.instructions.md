---
applyTo: '**'
---

## Cursor 代码生成规则（java-rule-new-table）

### 1. 实体类（Entity）生成规则

- **类名**：表名下划线转大驼峰，后缀 Entity（如 b_ap → BApEntity）
- **注解**：  
  - `@Data`  
  - `@EqualsAndHashCode(callSuper = false)`  
  - `@TableName("表名")`
- **字段**：  
  - 所有表字段，字段名与数据库一致（不用驼峰）
  - 字段注释：每个字段都要带上数据库中的注释
  - 特殊字段处理：  
    /**
     * 创建人id
     */
    @TableField(value="c_id", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long c_id;

    /**
     * 创建时间
     */
    @TableField(value="c_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private LocalDateTime c_time;

    /**
     * 修改人id
     */
    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long u_id;

    /**
     * 修改时间
     */
    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion")
    private Integer dbversion;

    - 其他字段：`@TableField("字段名")`
- **类注释**：表注释

---

### 2. VO类（Vo）生成规则

- **注解**：  
  - `@Data`  
  - `@EqualsAndHashCode(callSuper = false)`
- **字段**：  
  - 所有表字段，字段名与数据库一致（不用驼峰）
  - 字段注释：每个字段都要带上数据库中的注释
- **类注释**：表注释

---

### 3. Mapper类生成规则

- **类名**：表名下划线转大驼峰，后缀 Mapper（如 b_ap → BApMapper）
- **注解**：`@Mapper`
- **继承**：`BaseMapper<BApEntity>`
- **内容**：空接口，无需自定义方法

---

### 4. Service接口生成规则

- **类名**：I + 表名下划线转大驼峰 + Service（如 b_ap → IBApService）
- **继承**：`IService<BApEntity>`
- **内容**：空接口，无需自定义方法

---

### 5. ServiceImpl实现类生成规则

- **类名**：表名下划线转大驼峰 + ServiceImpl（如 b_ap → BApServiceImpl）
- **注解**：`@Service`
- **继承**：`ServiceImpl<BApMapper, BApEntity> implements IBApService`
- **内容**：空实现类，无需自定义方法

---

### 6. 其他说明

- 字段类型、注释、特殊注解需严格参照数据库表结构和字段注释。
- 生成的所有类都需带有标准的 JavaDoc 注释，内容为表或字段的注释。
- 生成的包路径需与实际项目结构保持一致。
- 仅生成空类/接口，不包含任何自定义方法。

---

### 7. 示例（以 b_ap 表为例）

#### BApEntity.java
```java
/**
 * 表注释
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("b_ap")
public class BApEntity implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "c_id", type = IdType.AUTO)
    private Integer c_id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date c_time;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date u_time;

    /**
     * 创建人ID
     */
    private Integer u_id;

    /**
     * 版本号
     */
    @Version
    private Integer dbversion;

    // 其他字段...
}
```

#### BApVo.java
```java
/**
 * 表注释
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BApVo implements Serializable {
    /**
     * 主键ID
     */
    private Integer c_id;

    /**
     * 创建时间
     */
    private Date c_time;

    /**
     * 更新时间
     */
    private Date u_time;

    /**
     * 创建人ID
     */
    private Integer u_id;

    /**
     * 版本号
     */
    private Integer dbversion;

    // 其他字段...
}
```

#### BApMapper.java
```java
@Mapper
public interface BApMapper extends BaseMapper<BApEntity> {
}
```

#### IBApService.java
```java
public interface IBApService extends IService<BApEntity> {
}
```

#### BApServiceImpl.java
```java
@Service
public class BApServiceImpl extends ServiceImpl<BApMapper, BApEntity> implements IBApService {
}
```

---

**将上述内容保存为 `.cursorrules` 文件，命名为 `java-rule-new-table`，即可在 Cursor 中自动生成符合规范的 Java 代码。**