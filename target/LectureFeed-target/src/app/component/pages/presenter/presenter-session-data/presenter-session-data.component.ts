import { Component, OnInit } from '@angular/core';
import {
  AbstractSessionManagementComponent,
  IAbstractSessionManagementComponent
} from "../../../organisms/abstract-session-management/abstract-session-management.component";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, MessageService} from "primeng/api";
import {SessionService} from "../../../../service/sessionService/session.service";
import {select, Store} from "@ngrx/store";
import {IAppAdminState} from "../../../../state/admin/app.admin.state";
import {firstValueFrom} from "rxjs";
import {take} from "rxjs/operators";
import {WaitDialogService} from "../../../../service/waitDialogService/wait-dialog.service";
import {Session} from "../../../../model/session/session.model";
import {ICustomColumnHeader} from "../../../molecules/question-table-overview/question-table-overview.component";
import FileSaver from 'file-saver';
import {selectToken} from "../../../../state/token/token.selector";
import {CustomRouterService} from "../../../../service/customRouter/custom-router.service";
import {MenuItem} from 'primeng/api'

@Component({
  selector: 'app-presenter-session-data',
  templateUrl: './presenter-session-data.component.html',
  styleUrls: ['./presenter-session-data.component.scss']
})
export class PresenterSessionDataComponent extends AbstractSessionManagementComponent implements IAbstractSessionManagementComponent, OnInit  {

  visibleSidebar: boolean = false;
  session: Session|null = null;
  items: MenuItem[] = [];
  home: MenuItem

  constructor(
    protected readonly router: Router,
    protected readonly route: ActivatedRoute,
    protected readonly messageService: MessageService,
    protected readonly sessionService: SessionService,
    private confirmationService: ConfirmationService,
    private readonly waitDialogService: WaitDialogService,
    private readonly store: Store<IAppAdminState>,
    private readonly customRouterService: CustomRouterService
  ) {
    super(router, route, messageService);
    this.home = {icon: 'pi pi-home', id: 'home'};
  }

  customColumnHeaders: ICustomColumnHeader[] = [
    {
      title: "Closed",
      field: "closed",
      sort: true
    }
  ]

  ngOnInit(){
    this.validateSession().then(_ => {
        this.waitDialogService.open("Load Survey");
        this.sessionService.getSession(this.getSessionId()).then(session => {
          this.waitDialogService.close();
          this.session = session;
          this.items.push({label: this.session.name})
        })
    });
  }

  protected getToken()
  {
    return firstValueFrom(this.store.pipe(select(selectToken), take(1)))
  }


  getQuestions(){
    if(this.session == null) return [];
    return Object.values(this.session.questions);
  }

  getSurveys(){
    if(this.session == null) return [];
    return Object.values(this.session.surveys);
  }

  getMoodEntities(){
    if(this.session == null) return [];
    return this.session.moodEntities;
  }

  getParticipants() {
    if(this.session == null) return [];
    return this.session.participants
  }

  onDeleteSession() {
    this.visibleSidebar = false;
    this.confirmationService.confirm({
      header: 'Confirm',
      message: 'Do you really want to delete this session?',
      accept: () => {
        this.sessionService.deleteSession(this.getSessionId() as number).then(() => {
          this.customRouterService.navigateWithObserverQueryParams(['/presenter']);
        });
      }
    });
  }

  onDownloadData() {
    this.visibleSidebar = false;
    this.confirmationService.confirm({
      header: 'Confirm',
      message: 'Do you want download this session data?',
      accept: () => {
        this.waitDialogService.open("Load Data");
        this.sessionService.getSessionCSVZip(this.getSessionId()).then(data =>  {
          if(data!=null) FileSaver.saveAs(data, `${this.session?.name}.zip`)
          this.waitDialogService.close();
        })
      }
    });
  }

  onClickBreadcrumbItem(e: any) {
    if(e.hasOwnProperty('item') && e.item.hasOwnProperty("id")){
      switch (e.item.id) {
        case 'home': this.customRouterService.navigateWithObserverQueryParams(['/presenter'])
      }
    }
  }
}
