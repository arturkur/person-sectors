import { SelectionModel } from '@angular/cdk/collections';
import { NestedTreeControl } from '@angular/cdk/tree';
import { Component } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { SnackBarService } from '../modals/message-snackbar/snack-bar.service';
import { Person, Sector } from '../models/api-objects.interface';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-person-sectors',
  templateUrl: './person-sectors.component.html',
  styleUrls: ['./person-sectors.component.css']
})
export class PersonSectorsComponent {
  personForm: FormGroup;
  treeControl: NestedTreeControl<Sector>;
  dataSource: MatTreeNestedDataSource<Sector>;
  checklistSelection: SelectionModel<Sector>;

  constructor(private apiService: ApiService,
    private snackBarService: SnackBarService) {
    this.personForm = new FormGroup({
      name: new FormControl(),
      sectors: new FormArray([], [Validators.required]),
      termsAccepted: new FormControl(false)
    });
    this.treeControl = new NestedTreeControl<Sector>(node => node.children);
    this.dataSource = new MatTreeNestedDataSource<Sector>();
    this.checklistSelection = new SelectionModel<Sector>(true);

    apiService.getSectors().subscribe((response) => {
      if (response.body) {
        this.dataSource.data = response.body;
        this.loadPersonFromSession();
      }
    });
  }

  hasChildren = (_: number, node: Sector) => {
    return node.children && node.children.length > 0;
  }

  sectorItemSelectionToggle(node: Sector, event: any) {
    const formArray: FormArray = this.personForm.get("sectors") as FormArray;
    if (event.checked) {
      formArray.push(new FormControl(node.id));
    } else {
      let i: number = 0;
      formArray.controls.forEach((ctrl) => {
        if (ctrl.value == node.id) {
          formArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  isCheckedSector(id: string) {
    const formArray: string[] = this.personForm.get("sectors")?.value;
    return formArray.indexOf(id) > -1;
  }

  savePersonData() {
    let sectors: string[] = this.personForm.value.sectors;
    sectors = sectors.filter(x => x !== null);
    if (this.personForm.valid && sectors.length > 0) {
      const person: Person = {
        name: this.personForm.value.name,
        termsAccepted: this.personForm.value.termsAccepted,
        selectedSectors: sectors
      };
      this.apiService.updatePerson(person).subscribe((response) => {
        if (response.body) {
          const savedPerson: Person = response.body;
          this.patchFormValues(savedPerson);
          sessionStorage.setItem("latestName", savedPerson.name);
          this.snackBarService.openSnackBar("Successfully saved to database!", false);
        }
      });
    } else {
      this.snackBarService.openSnackBar("All fields are mandatory!", true);
    }
  }

  loadPersonFromSession() {
    const nameInSession = sessionStorage.getItem("latestName");
    if (nameInSession) {
      this.apiService.getPersonByName(nameInSession).subscribe((response) => {
        if (response.body) {
          this.patchFormValues(response.body);
        }
      });
    }
  }

  patchFormValues(person: Person) {
    this.personForm.reset();
    const formArray: FormArray = this.personForm.get("sectors") as FormArray;
    person.selectedSectors.forEach((sectorId) => {
      formArray.push(new FormControl(sectorId));
      this.expandSector(this.dataSource.data, sectorId);
    });
    this.personForm.patchValue(person);
  }

  expandSector(data: Sector[], sectorId: string) {
    data.forEach((node) => {
      if (node.children && node.children.find(x => x.id === sectorId)) {
        this.treeControl.expand(node);
        this.expandSector(this.dataSource.data, node.id);
      } else if (node.children && node.children.find(x => x.children)) {
        this.expandSector(node.children, sectorId);
      }
    })
  }

}
