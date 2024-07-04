// Copyright (c) 2004, 2021, Oracle and/or its affiliates. All rights reserved.
package asq.pos;

import java.util.Date;

import dtv.util.DateUtils;

/**
 * A class which reports version information for the base customer overlay.
 * 
 * @author jweiss
 * @created Jun 1, 2007
 */
public class Version
    extends dtv.pos.Version {

  private static final String CUSTOMER_VERSION = "1.1.1";
  private static final String PATCH_VERSION = "0.0";
  private static final String BUILD_DATE = "2024-04-19T12:36:19+0530";

  /** {@inheritDoc} */
  @Override
  protected Date getCustomerBuildDateImpl() {
    return DateUtils.parseIso(BUILD_DATE);
  }

  /** {@inheritDoc} */
  @Override
  protected String getCustomerVersionImpl() {
    return CUSTOMER_VERSION;
  }

  /** {@inheritDoc} */
  @Override
  protected String getPatchVersionImpl() {
    return PATCH_VERSION;
  }
}
