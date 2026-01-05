package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.logging.Logger;


/**
 * The persistent class for the proveedor_catalogo database table.
 * 
 */
@Entity
@Table(name="proveedor_catalogo")
public class ProveedorCatalogo extends BaseDomain implements Identifiable<Integer>,Serializable {

		private static final Logger log = Logger.getLogger(ProveedorCatalogo.class.getName());
		private static final long serialVersionUID = 1L;



		@Id
		@Column(name="id_proveedor_catalogo", unique=true, nullable=false)
		@GeneratedValue(generator = "proveedor_catalogo_id_seq", strategy = GenerationType.SEQUENCE)
		@SequenceGenerator(name = "proveedor_catalogo_id_seq", sequenceName = "proveedor_catalogo_id_seq", allocationSize = 1)
		private Integer id;

		@Column(name="ruta_catalogo", nullable=false, length=1000)
		private String rutaCatalogo;

		//uni-directional many-to-one association to Proveedor
		@ManyToOne
		@JoinColumn(name="id_proveedor", nullable=false)
		private Proveedor proveedor;

		@Column(name="archivo_id", nullable=false, length=60)
		private String archivoId;

		@Column(name="archivo_nombre", nullable=false, length=100)
		private String archivoNombre;

		@Column(name="archivo_tipo", nullable=false, length=100)
		private String archivoTipo;

	@Override
	public String entityClassName() {
		return ProveedorCatalogo.class.getSimpleName();
	}
		@Override
		public void setId(Integer id) {
			this.id = id;
		}

		public ProveedorCatalogo id(Integer id) {
			setId(id);
			return this;
		}
	@Override
	public Integer getId() {
		return id;
	}
		@Override
		@Transient
		public boolean isIdSet() {
			return id != null;
		}
		public ProveedorCatalogo() {
		}



		public ProveedorCatalogo(Proveedor proveedor,String rutaCatalogo, String archivoId, String archivoNombre, String archivoTipo) {
			this.proveedor = proveedor;
			this.rutaCatalogo = rutaCatalogo;
			this.archivoId = archivoId;
			this.archivoNombre = archivoNombre;
			this.archivoTipo = archivoTipo;
		}

		public String getRutaCatalogo() {
			return this.rutaCatalogo;
		}

		public void setRutaCatalogo(String rutaCatalogo) {
			this.rutaCatalogo = rutaCatalogo;
		}

		public Proveedor getProveedor() {
			return this.proveedor;
		}

		public void setProveedor(Proveedor proveedor) {
			this.proveedor = proveedor;
		}

		public String getArchivoId() {
			return archivoId;
		}

		public void setArchivoId(String archivoId) {
			this.archivoId = archivoId;
		}

		public String getArchivoNombre() {
			return archivoNombre;
		}

		public void setArchivoNombre(String archivoNombre) {
			this.archivoNombre = archivoNombre;
		}

		public String getArchivoTipo() {
			return archivoTipo;
		}

		public void setArchivoTipo(String archivoTipo) {
			this.archivoTipo = archivoTipo;
		}

		public ProveedorCatalogo withDefaults() {
			return this;
		}

		/**
		 * Equals implementation using a business key.
		 */
		@Override
		public boolean equals(Object other) {
			return this == other || (other instanceof ProveedorCatalogo && hashCode() == other.hashCode());
		}

		@Override
		public String toString() {
			return "ProveedorCatalogo{" +
					"idProveedorCatalogo=" + id +
					", rutaCatalogo='" + rutaCatalogo + '\'' +
					", proveedor=" + proveedor +
					", archivoId='" + archivoId + '\'' +
					", archivoNombre='" + archivoNombre + '\'' +
					", archivoTipo='" + archivoTipo + '\'' +
					'}';
		}







}