package rog.service.dto;

import rog.domain.GlossaryOfPurposes;
import rog.domain.enumeration.AssignmentToCell;

import java.io.Serializable;
import java.util.Objects;

public class GlossaryOfPurposesDTO implements Serializable{

    private Long id;

    private String name;

    private Long parentId;

    private Boolean sumUp;

    private AssignmentToCell assignmentToCell;

    private Boolean isChecked;

    public GlossaryOfPurposesDTO() {
    }

    public GlossaryOfPurposesDTO(Long id, String name, Long parentId, Boolean sumUp, AssignmentToCell assignmentToCell,
                                 Boolean isChecked) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.sumUp = sumUp;
        this.assignmentToCell = assignmentToCell;
        this.isChecked = isChecked;
    }

    public GlossaryOfPurposesDTO(GlossaryOfPurposes glossaryOfPurposes){

        if(Objects.isNull(glossaryOfPurposes)){
            return;
        }

        this.id = glossaryOfPurposes.getId();
        this.name = glossaryOfPurposes.getName();
        this.parentId = glossaryOfPurposes.getParentId();
        this.sumUp = glossaryOfPurposes.isSumUp();
        this.assignmentToCell = glossaryOfPurposes.getAssignmentToCell();
        this.isChecked = glossaryOfPurposes.getChecked();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getSumUp() {
        return sumUp;
    }

    public void setSumUp(Boolean sumUp) {
        this.sumUp = sumUp;
    }

    public AssignmentToCell getAssignmentToCell() {
        return assignmentToCell;
    }

    public void setAssignmentToCell(AssignmentToCell assignmentToCell) {
        this.assignmentToCell = assignmentToCell;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryOfPurposesDTO that = (GlossaryOfPurposesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(sumUp, that.sumUp) &&
            assignmentToCell == that.assignmentToCell &&
            Objects.equals(isChecked, that.isChecked);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, parentId, sumUp, assignmentToCell, isChecked);
    }
}
