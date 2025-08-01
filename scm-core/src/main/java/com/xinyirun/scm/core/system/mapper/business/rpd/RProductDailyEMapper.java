package com.xinyirun.scm.core.system.mapper.business.rpd;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.business.rpd.RProductDailyEEntity;
import com.xinyirun.scm.bean.system.vo.business.rpd.BProductDailyVo;
import com.xinyirun.scm.common.constant.DictConstant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 稻壳 加工日报表 Mapper 接口
 * </p>
 *
 * @author xinyirun
 * @since 2023-05-16
 */
@Repository
public interface RProductDailyEMapper extends BaseMapper<RProductDailyEEntity> {

    @Delete(""
            + " DELETE FROM                                                                                             "
            + "   r_product_daily_e                                                                                     "
            + " WHERE date_format(date, '%Y%m%d') = date_format(#{p1.date}, '%Y%m%d')                                        "
//            + " AND (date_format(date, '%Y-%m-%d') >= #{p1.start_time} or #{p1.start_time} is null)                     "
//            + " AND (date_format(date, '%Y-%m-%d') <= #{p1.end_time} or #{p1.end_time} is null)                         "
    )
    void deleteR_product_daily_e_61(@Param("p1") BProductDailyVo vo);


    @Insert(""
            +  "  INSERT INTO r_product_daily_e ( date, warehouse_id, in_qty, out_qty, residue_qty, c_time ) SELECT     "
            +  "  tab1.date,                                                                                            "
            +  "  tab1.warehouse_id,                                                                                    "
            +  "  tab2.in_qty,                                                                                          "
            +  "  tab3.out_qty,                                                                                         "
            +  "  tab5.residue_qty,                                                                                     "
            +  "  now()                                                                                                 "
            +  "  FROM                                                                                                  "
            +  "  	(                                                                                                   "
            +  "  	SELECT                                                                                              "
            +  "  		t.id warehouse_id,                                                                              "
            +  "  		t1.date                                                                                         "
            +  "  	FROM                                                                                                "
            +  "  		m_warehouse t,                                                                                  "
            +  "  		s_calendar t1                                                                                   "
            +  "  	WHERE                                                                                               "
            +  "        date_format(date, '%Y%m%d') = date_format(#{p1.date}, '%Y%m%d')                                      "
//            +  "  		AND (date_format(date, '%Y-%m-%d') >= #{p1.start_time} or #{p1.start_time} is null)             "
//            +  "  		AND (date_format(date, '%Y-%m-%d') <= #{p1.end_time} or #{p1.end_time} is null)                 "
            +  "  		AND t.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                            "
            +  "  		AND t.`enable` = '1'                                                                            "
            +  "  	) tab1                                                                                              "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.actual_weight ) in_qty,                                                                   "
            +  "  		DATE_FORMAT( t.e_dt, '%Y-%m-%d' ) date,                                                         "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		b_in t                                                                                          "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
//            +  "  		t.type = '"+ DictConstant.DICT_B_IN_TYPE_SC +"'                                                                                    "
            +  "  		AND t.`status` = '2'                                                                            "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  		AND t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                           "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  		t.warehouse_id,                                                                                 "
            +  "  		DATE_FORMAT( t.e_dt, '%Y%m%d' )                                                                 "
            +  "  	) tab2 ON tab1.warehouse_id = tab2.warehouse_id                                                     "
            +  "  	AND tab1.date = tab2.date                                                                           "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.actual_weight ) out_qty,                                                                  "
            +  "  		DATE_FORMAT( t.e_dt, '%Y-%m-%d' ) date,                                                         "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		b_out t                                                                                         "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
//            +  "  		t.type = '"+ DictConstant.DICT_B_OUT_TYPE_XS + "'                                               "
            +  "  		AND t.`status` = '2'                                                                            "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  		AND t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                           "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  		t.warehouse_id,                                                                                 "
            +  "  		DATE_FORMAT( t.e_dt, '%Y%m%d' )                                                                 "
            +  "  	) tab3 ON tab1.warehouse_id = tab3.warehouse_id                                                     "
            +  "  	AND tab1.date = tab3.date                                                                           "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.qty_avaible ) residue_qty,                                                                       "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		m_inventory t                                                                             "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
            +  "  		t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                               "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  	t.warehouse_id                                                                                      "
            +  "  	) tab5 ON tab1.warehouse_id = tab5.warehouse_id                                                     "
            +  "  WHERE                                                                                                 "
//            +  "   (tab2.in_qty is not null or tab3.out_qty is not null or tab5.residue_qty is not null)                "
            +  "   (ifnull(tab2.in_qty, 0) != 0 or ifnull(tab3.out_qty, 0) != 0 or ifnull(tab5.residue_qty, 0) != 0)                "
    )
    void insertR_product_daily_e_62(@Param("p1") BProductDailyVo vo);

    @Delete(""
            + " DELETE FROM                                                                                             "
            + "   r_product_daily_e                                                                                     "
            + " WHERE TRUE                                                                                              "
            + " AND (date_format(date, '%Y-%m-%d') >= #{p1.init_time} or #{p1.init_time} is null or #{p1.init_time} = '')"
            + " AND (date_format(date, '%Y-%m-%d') <= #{p1.end_time} or #{p1.end_time} is null or #{p1.end_time} = '' ) "
            + " AND (warehouse_id = #{p1.warehouse_id} or #{p1.warehouse_id} is null or #{p1.warehouse_id} = '')        "
    )
    void deleteR_product_daily_e_601(@Param("p1") BProductDailyVo vo);


    @Insert(""
            +  "  INSERT INTO r_product_daily_e ( date, warehouse_id, in_qty, out_qty, residue_qty, c_time ) SELECT     "
            +  "  tab1.date,                                                                                            "
            +  "  tab1.warehouse_id,                                                                                    "
            +  "  tab2.in_qty,                                                                                          "
            +  "  tab3.out_qty,                                                                                         "
            +  "  tab5.residue_qty,                                                                                     "
            +  "  now()                                                                                                 "
            +  "  FROM                                                                                                  "
            +  "  	(                                                                                                   "
            +  "  	SELECT                                                                                              "
            +  "  		t.id warehouse_id,                                                                              "
            +  "  		t1.date                                                                                         "
            +  "  	FROM                                                                                                "
            +  "  		m_warehouse t,                                                                                  "
            +  "  		s_calendar t1                                                                                   "
            +  "  	WHERE TRUE                                                                                          "
//            +  "        date_format(date, '%Y%m%d') = date_format(p1.date, '%Y%m%d')                                      "
            +  "  		AND (date_format(date, '%Y-%m-%d') >= #{p1.init_time} or #{p1.init_time} is null)               "
            +  "  		AND (date_format(date, '%Y-%m-%d') <= #{p1.end_time} or #{p1.end_time} is null)                 "
            +  "        AND (t.id = #{p1.warehouse_id} or #{p1.warehouse_id} is null or #{p1.warehouse_id} = '')        "
            +  "  		AND t.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                            "
            +  "  		AND t.`enable` = '1'                                                                            "
            +  "  	) tab1                                                                                              "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.actual_weight ) in_qty,                                                                   "
            +  "  		DATE_FORMAT( t.e_dt, '%Y-%m-%d' ) date,                                                         "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		b_in t                                                                                          "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
//            +  "  		t.type = '"+ DictConstant.DICT_B_IN_TYPE_SC +"'                                                                                    "
            +  "  		AND t.`status` = '2'                                                                            "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  		AND t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                           "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  		t.warehouse_id,                                                                                 "
            +  "  		DATE_FORMAT( t.e_dt, '%Y%m%d' )                                                                 "
            +  "  	) tab2 ON tab1.warehouse_id = tab2.warehouse_id                                                     "
            +  "  	AND tab1.date = tab2.date                                                                           "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.actual_weight ) out_qty,                                                                  "
            +  "  		DATE_FORMAT( t.e_dt, '%Y-%m-%d' ) date,                                                         "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		b_out t                                                                                         "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
//            +  "  		t.type = '"+ DictConstant.DICT_B_OUT_TYPE_XS + "'                                               "
            +  "  		AND t.`status` = '2'                                                                            "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  		AND t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                           "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  		t.warehouse_id,                                                                                 "
            +  "  		DATE_FORMAT( t.e_dt, '%Y%m%d' )                                                                 "
            +  "  	) tab3 ON tab1.warehouse_id = tab3.warehouse_id                                                     "
            +  "  	AND tab1.date = tab3.date                                                                           "
            +  "  	LEFT JOIN (                                                                                         "
            +  "  	SELECT                                                                                              "
            +  "  		SUM( t.qty ) residue_qty,                                                                       "
            +  "        DATE_FORMAT(t.dt,'%Y-%m-%d') dt,                                                                "
            +  "  		t.warehouse_id                                                                                  "
            +  "  	FROM                                                                                                "
            +  "  		b_daily_inventory t                                                                             "
            +  "  		LEFT JOIN m_goods_spec t1 ON t.sku_id = t1.id                                                   "
            +  "  		LEFT JOIN m_warehouse t2 ON t.warehouse_id = t2.id                                              "
            +  "  	WHERE                                                                                               "
            +  "  		t2.warehouse_type = '"+ DictConstant.DICT_M_WAREHOUSE_TYPE_WD +"'                               "
            +  "  		AND t2.`enable` = '1'                                                                           "
            +  "  		AND t1.goods_code = #{p1.p00000128Vo.column_five}                                                           "
            +  "  	GROUP BY                                                                                            "
            +  "  	t.warehouse_id,                                                                                     "
            +  "    DATE_FORMAT(t.dt,'%Y%m%d')                                                                          "
            +  "  	) tab5 ON tab1.warehouse_id = tab5.warehouse_id   AND  tab5.dt = tab1.date                          "
            +  "  WHERE                                                                                                 "
//            +  "   (tab2.in_qty is not null or tab3.out_qty is not null or tab5.residue_qty is not null)                "
            +  "   (ifnull(tab2.in_qty, 0) != 0 or ifnull(tab3.out_qty, 0) != 0 or ifnull(tab5.residue_qty, 0) != 0)                "
    )
    void insertR_product_daily_e_602(@Param("p1") BProductDailyVo vo);
}
