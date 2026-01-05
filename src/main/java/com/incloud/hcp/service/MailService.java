package com.incloud.hcp.service;


//@Service("mailService")
public class MailService {
/*
    private static Logger log = LoggerFactory.getLogger(MailService.class);


    private MailConfiguration mailConfig;

    private Configuration freemarkerConfiguration;

    @Autowired
    ParametroRepository parameterRepository;

    @Autowired
    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Autowired
    public void setMailConfig(MailConfiguration mailConfig){
        this.mailConfig = mailConfig;
    }

    public void sendEmail(Map<String, Object> dataTemplate, Map<String, Object> smtpMap,
                          String emailTo, String subject, String template) {


        MimeMessagePreparator preparator = getMessagePreparator(dataTemplate, smtpMap, emailTo, subject, template);

        try {

            JavaMailSender mailSender = mailConfig.getMailSender(smtpMap);

            mailSender.send(preparator);
            log.debug("El mensaje ha sido enviado");
        } catch (MailException ex) {

            log.error("Error al enviar mensaje: " + ex.getMessage());
        }
    }


    private MimeMessagePreparator getMessagePreparator(Map<String, Object> dataTemplate, Map<String, Object> smtpMap,
                                                       final String emailTo, final String subject, final String template) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/com/incloud/hcp/templates");

                Template t = freemarkerConfiguration.getTemplate(template);

                String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, dataTemplate);

                helper.setSubject(subject);
                helper.setFrom(smtpMap.get("remitentEmail").toString(), smtpMap.get("remitentName").toString());
                helper.setTo(emailTo);

                helper.setText(text, true);

                //Additionally, let's add a resource as an attachment as well.
                //helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));
            }
        };
        return preparator;
    }

*/
}
