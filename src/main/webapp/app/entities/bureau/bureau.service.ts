import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBureau } from 'app/shared/model/bureau.model';

type EntityResponseType = HttpResponse<IBureau>;
type EntityArrayResponseType = HttpResponse<IBureau[]>;

@Injectable({ providedIn: 'root' })
export class BureauService {
  public resourceUrl = SERVER_API_URL + 'api/bureaus';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/bureaus';

  constructor(protected http: HttpClient) {}

  create(bureau: IBureau): Observable<EntityResponseType> {
    return this.http.post<IBureau>(this.resourceUrl, bureau, { observe: 'response' });
  }

  update(bureau: IBureau): Observable<EntityResponseType> {
    return this.http.put<IBureau>(this.resourceUrl, bureau, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBureau>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBureau[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBureau[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
