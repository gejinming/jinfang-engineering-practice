package com.jinfang.service;

import com.jinfang.entity.CcStudent;

/**
 * @description:
 * @author: Gjm
 * @create: 2021-01-12 16:41
 **/

public interface CcStudentService extends CurdService<CcStudent>  {

    CcStudent findByStudentLogin(String studentNo,String password);

    boolean findStudentById(Long id);

    CcStudent findInfoById(Long id);
}
