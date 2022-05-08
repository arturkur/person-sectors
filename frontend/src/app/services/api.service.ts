import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Person, Sector } from '../models/api-objects.interface';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private url: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getSectors() {
    return this.http.get<Sector[]>(this.url + "/sectors", { observe: "response" });
  }

  updatePerson(person: Person) {
    return this.http.post<Person>(this.url + "/person", person, { observe: "response" });
  }

  getPersonByName(name: string) {
    return this.http.get<Person>(this.url + "/person?name=" + name, { observe: "response" });
  }
}
