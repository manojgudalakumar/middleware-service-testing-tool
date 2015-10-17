package com.github.tomakehurst.wiremock.jetty6;

import java.io.IOException;
import java.net.Socket;

import org.mortbay.jetty.bio.SocketConnector;

import com.github.tomakehurst.wiremock.global.RequestDelayControl;

class DelayableSocketConnector extends SocketConnector {

  private final RequestDelayControl requestDelayControl;

  DelayableSocketConnector(final RequestDelayControl requestDelayControl) {
    this.requestDelayControl = requestDelayControl;
  }

  @Override
  public void accept(final int acceptorID) throws IOException, InterruptedException {
    final Socket socket = _serverSocket.accept();

    try {
      requestDelayControl.delayIfRequired();
    } catch (InterruptedException e) {
      if (!(isStopping() || isStopped())) {
        Thread.interrupted(); // Clear the interrupt flag on the current
        // thread
      }
    }

    configure(socket);
    Connection connection = new Connection(socket) {

      @Override
      public void run() {
        ActiveSocket.set(socket);
        super.run();
        ActiveSocket.clear();
      }
    };
    connection.dispatch();
  }
}
