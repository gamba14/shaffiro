/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoNoAsociadoDeleteDialogComponent } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado-delete-dialog.component';
import { DispositivoNoAsociadoService } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado.service';

describe('Component Tests', () => {
    describe('DispositivoNoAsociado Management Delete Component', () => {
        let comp: DispositivoNoAsociadoDeleteDialogComponent;
        let fixture: ComponentFixture<DispositivoNoAsociadoDeleteDialogComponent>;
        let service: DispositivoNoAsociadoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoNoAsociadoDeleteDialogComponent]
            })
                .overrideTemplate(DispositivoNoAsociadoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DispositivoNoAsociadoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispositivoNoAsociadoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
