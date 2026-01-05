package com.incloud.hcp.service._framework;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.service.notificacion.MailSetting;
import com.incloud.hcp.util.constant.MailConstant;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BaseServiceImpl {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SystemLoggedUser systemLoggedUser;


	@Autowired
	private ParametroRepository parametroRepository;

	/*public User getUserSCP(HttpServletRequest request) {
		return this.systemLoggedUser.getUserSCP(request);
	}*/

	public UserSession getUserSession(Token token) throws PortalException {
		return this.systemLoggedUser.getUserSession(token);

	}



	protected Map<String, Object> getConfigSmtp(){

		//Obtenemos la configuraci�n del Servidor Smtp de la Compa�ia
		String host = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_HOST)).getValor();
		int port = Integer.parseInt((parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_PORT)).getValor());
		String email = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_USERNAME)).getValor();
		String password = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_PASSWORD)).getValor();
		String name = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_REMITENT_NAME)).getValor();

		Map<String, Object> smtpLocal = new HashMap<String, Object>();
		
	    smtpLocal.put("host", host);
	    smtpLocal.put("port", port);
	    smtpLocal.put("remitentEmail", email);
		smtpLocal.put("password", password);
	    smtpLocal.put("remitentName", name);

		return smtpLocal;
	}

	public MailSetting getMailSetting(){

		//Obtenemos la configuraci�n del Servidor Smtp de la Compa�ia
		String host = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_HOST)).getValor();
		int port = Integer.parseInt((parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_PORT)).getValor());
		String email = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_USERNAME)).getValor();
		String password = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_PASSWORD)).getValor();
		String name = (parametroRepository.getParametroByModuloAndTipo(MailConstant.KEY_GENERAL, MailConstant.KEY_MAIL_REMITENT_NAME)).getValor();

		MailSetting setting = new MailSetting();

		setting.setHost(host);
		setting.setPort(port + "");
		setting.setUser(email);
		setting.setPassword(password);
		setting.setNameFrom(name);

		return setting;
	}
	
}
