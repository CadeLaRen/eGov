/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.infra.filestore.entity;

import org.egov.infra.persistence.entity.AbstractPersistable;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "eg_filestoremap")
@Entity
@SequenceGenerator(name = FileStoreMapper.SEQ_FILESTOREMAPPER, sequenceName = FileStoreMapper.SEQ_FILESTOREMAPPER, allocationSize = 1)
public class FileStoreMapper extends AbstractPersistable<Long> {
    private static final long serialVersionUID = -2997164207274266823L;
    public static final String SEQ_FILESTOREMAPPER = "SEQ_EG_FILESTOREMAP";

    @DocumentId
    @Id
    @GeneratedValue(generator = SEQ_FILESTOREMAPPER, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(length = 36, unique = true, nullable = false)
    private String fileStoreId;

    @NotBlank
    private String fileName;

    private String contentType;

    protected FileStoreMapper() {
        // For JPA
    }

    public FileStoreMapper(final String fileStoreId, final String fileName) {
        this.fileStoreId = fileStoreId;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFileStoreId() {
        return fileStoreId;
    }

    public void setFileStoreId(final String fileStoreId) {
        this.fileStoreId = fileStoreId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

}