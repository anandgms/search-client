package edu.anand.search.api.util;

import edu.anand.search.api.service.SearchClient;

public abstract class AbstractSearchClientBuilder {

  protected String host;
  protected String user;
  protected String password;

  public SearchClient build() {
    return createClient();
  }

  protected abstract SearchClient createClient();

  protected String host() {
    return this.host;
  }

  public AbstractSearchClientBuilder host(String host) {
    this.host = host;
    return this;
  }

  public AbstractSearchClientBuilder user(String user) {
    this.user = user;
    return this;
  }

  public AbstractSearchClientBuilder password(String password) {
    this.password = password;
    return this;
  }
}
