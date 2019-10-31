/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShaffiroTestModule } from '../../../test.module';
import { ReglaDeleteDialogComponent } from 'app/entities/regla/regla-delete-dialog.component';
import { ReglaService } from 'app/entities/regla/regla.service';

describe('Component Tests', () => {
    describe('Regla Management Delete Component', () => {
        let comp: ReglaDeleteDialogComponent;
        let fixture: ComponentFixture<ReglaDeleteDialogComponent>;
        let service: ReglaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [ReglaDeleteDialogComponent]
            })
                .overrideTemplate(ReglaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReglaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReglaService);
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
