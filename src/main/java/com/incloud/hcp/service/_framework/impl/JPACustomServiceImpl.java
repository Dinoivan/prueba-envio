package com.incloud.hcp.service._framework.impl;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.BaseResponseDomain;
import com.incloud.hcp.service._framework.JPACustomService;
import com.incloud.hcp.service.support.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class JPACustomServiceImpl<R extends BaseResponseDomain, T extends BaseDomain> implements JPACustomService<R, T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MessageSource messageSource;

    @PersistenceContext
    private EntityManager entityManager;



    /*****************************/
    /* Metodos de Busqueda       */
    /*****************************/


    @Transactional(readOnly = true)
    public List<T> findCondicion(R req) throws Exception {
        log.debug("Ingresando findCondicion: ", req);
        R bean = req;
        Sort sort = Sort.by("id");
        sort = this.setFindCondicion(sort);
        List<T> lista = new ArrayList<T>();
        lista = this.getDataPredicate(req);
        return lista;
    }
    protected Sort setFindCondicion(Sort sort) {
        return sort;
    }
    protected abstract T setObtenerBeanResponse(R bean) ;
    protected abstract Class<T> setObtenerClassBean();
    protected List<Predicate> setAbstractPredicate(R bean, CriteriaBuilder cb, Root<T> root) {
        return null;
    }
    protected List<T> getDataPredicate(R bean) throws Exception {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        T beanEntity = this.setObtenerBeanResponse(bean);
        if (!Optional.ofNullable(beanEntity).isPresent()) {
            String msg = this.messageSource.getMessage("message.beanEntity.requerido", null, LocaleContextHolder.getLocale());
            throw new Exception(msg);
        }
        CriteriaQuery<T> query= cb.createQuery(this.setObtenerClassBean());
        Root<T> root = query.from(this.setObtenerClassBean());

        List<Predicate> predicates = this.setAbstractPredicate(bean, cb, root);
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));

        Stream<T> listaStream = this.entityManager.createQuery(query).getResultStream();
        return listaStream.collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public PageResponse<T> findCondicionPaginated(R req, PageRequest pageRequest) throws Exception {
        log.debug("Ingresando findCondicionPaginated: ", req);
        R bean = req;
        Sort sort = Sort.by("id");
        sort = this.setFindCondicion(sort);
        Page<T> page =  this.getDataPredicatePaginated(req, pageRequest);
        List<T> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }
    protected Page<T> getDataPredicatePaginated(R bean, PageRequest pageRequest) throws Exception {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        T beanEntity = this.setObtenerBeanResponse(bean);
        if (!Optional.ofNullable(beanEntity).isPresent()) {
            String msg = this.messageSource.getMessage("message.beanEntity.requerido", null, LocaleContextHolder.getLocale());
            throw new Exception(msg);
        }
        CriteriaQuery<T> query= cb.createQuery(this.setObtenerClassBean());
        Root<T> root = query.from(this.setObtenerClassBean());

        List<Predicate> predicates = this.setAbstractPredicate(bean, cb, root);
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<T> tquery = this.entityManager.createQuery(query);
        tquery.setFirstResult(pageRequest.getPageNumber());
        tquery.setMaxResults(pageRequest.getPageSize());

        Predicate[] predicateArray = null;
        if (predicates != null && predicates.size() > 0) {
            predicateArray = new Predicate[predicates.size()];
            for(int i=0; i < predicates.size(); i++) {
                predicateArray[i] = (Predicate)predicates.get(i);
            }
        }
        return new PageImpl<T>(
                tquery.getResultList(),
                pageRequest,
                this.getTotalCountPaginated(cb, root,predicateArray));
    }
    private Long getTotalCountPaginated(CriteriaBuilder criteriaBuilder, Root<T> root, Predicate[] predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        if (predicateArray != null && predicateArray.length > 0) {
            criteriaQuery.where(predicateArray);
        }
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }



    /************************/
    /* Instancia de Bean    */
    /************************/

    /**
     * Converts the passed moduleSystem to a DTO.
     */
    protected final T toDTO(T bean) {
        return bean;
    }
    protected abstract T createInstance();



}